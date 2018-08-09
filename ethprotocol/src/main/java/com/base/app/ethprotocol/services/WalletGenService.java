package com.base.app.ethprotocol.services;

/**
 * @Auth: WangZhuang
 * @CreateTime: 2018/5/7
 * @describe：
 */

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;

import com.base.app.ethprotocol.data.FullWallet;
import com.base.app.ethprotocol.data.bean.CreateETHAdressSucced;
import com.base.app.ethprotocol.interfaces.StorableWallet;
import com.base.app.ethprotocol.utils.AddressNameConverter;
import com.base.app.ethprotocol.utils.OwnWalletUtils;
import com.base.app.ethprotocol.utils.Settings;
import com.base.app.ethprotocol.utils.WalletStorage;

import de.greenrobot.event.EventBus;


public class WalletGenService extends IntentService {

    private NotificationCompat.Builder builder;
    final int mNotificationId = 152;

    private boolean normalMode = true;
    CreateETHAdressSucced succed;

    public WalletGenService() {
        super("WalletGen Service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String password = intent.getStringExtra("PASSWORD");
        String name = intent.getStringExtra("NAME");
        String privatekey = "";

        if (intent.hasExtra("PRIVATE_KEY")) {
            normalMode = false;
            privatekey = intent.getStringExtra("PRIVATE_KEY").trim();
        }

        //sendNotification();
        try {
            String walletAddress = "";
            String keyStore = "";

            if (normalMode) { // Create new key
                String addressAndKeyStore = OwnWalletUtils.generateNewWalletFile(password, new File(this.getFilesDir(), ""), true, true);
                String[] addressAndKeyStoreArray = addressAndKeyStore.split(" ");
                walletAddress = addressAndKeyStoreArray[0];
                keyStore = addressAndKeyStoreArray[1];
                succed = new CreateETHAdressSucced();
                succed.setState(1);
            } else { // Privatekey passed
                //ECKeyPair keys = ECKeyPair.create(Hex.decode(privatekey));
                WalletFile walletFile = new WalletFile();
                try {
                    JSONObject jsonObject = new JSONObject(privatekey.toLowerCase());
                    String address = jsonObject.getString("address");
                    String id = jsonObject.getString("id");
                    int version = jsonObject.getInt("version");
                    WalletFile.Crypto crypto = new WalletFile.Crypto();
                    JSONObject jsonObject1 = jsonObject.getJSONObject("crypto");
                    String cipher = jsonObject1.getString("cipher");
                    String ciphertext = jsonObject1.getString("ciphertext");
                    String kdf = jsonObject1.getString("kdf");
                    String mac = jsonObject1.getString("mac");
                    JSONObject jsonObject2 = jsonObject1.getJSONObject("cipherparams");
                    WalletFile.CipherParams cipherparams = new WalletFile.CipherParams();
                    String iv = jsonObject2.getString("iv");
                    JSONObject jsonObject3 = jsonObject1.getJSONObject("kdfparams");

                    int dklen = jsonObject3.getInt("dklen");
                    int n = jsonObject3.getInt("n");
                    int r = jsonObject3.getInt("r");
                    int p = jsonObject3.getInt("p");
                    String salt = jsonObject3.getString("salt");
                    WalletFile.ScryptKdfParams kdfparams = new WalletFile.ScryptKdfParams();
                    kdfparams.setDklen(dklen);
                    kdfparams.setN(n);
                    kdfparams.setP(p);
                    kdfparams.setR(r);
                    kdfparams.setSalt(salt);

                    walletFile.setAddress(address);
                    walletFile.setId(id);
                    walletFile.setVersion(version);
                    crypto.setCipher(cipher);
                    crypto.setCiphertext(ciphertext);
                    crypto.setKdf(kdf);
                    crypto.setMac(mac);
                    cipherparams.setIv(iv);
                    crypto.setCipherparams(cipherparams);
                    crypto.setKdfparams(kdfparams);
                    walletFile.setCrypto(crypto);

                    ECKeyPair keys = Wallet.decrypt(password, walletFile);
                    walletAddress = OwnWalletUtils.generateWalletFile(password, keys, new File(this.getFilesDir(), ""), true, false);
                    succed = new CreateETHAdressSucced();
                    succed.setState(5);
                } catch (JSONException e) {
                    e.printStackTrace();
                    CreateETHAdressSucced succed = new CreateETHAdressSucced();
                    succed.setState(3);
                    EventBus.getDefault().post(succed);
                    return;
                }


            }
            ArrayList<StorableWallet> storedwallets = new ArrayList<StorableWallet>(WalletStorage.getInstance(this).get());
            for (StorableWallet cur : storedwallets) {
                if (("0x" + walletAddress).equals(cur.getPubKey())) {
                    CreateETHAdressSucced fail = new CreateETHAdressSucced();
                    fail.setState(0);
                    EventBus.getDefault().post(fail);
                    return;
                }
            }
            WalletStorage.getInstance(this).add(new FullWallet("0x" + walletAddress, walletAddress), this);
            if (TextUtils.isEmpty(name)) {
                AddressNameConverter.getInstance(this).put("0x" + walletAddress, "Wallet " + ("0x" + walletAddress).substring(0, 6), this);
            } else {
                AddressNameConverter.getInstance(this).put("0x" + walletAddress, name, this);
            }
            Settings.walletBeingGenerated = false;

            //finished("0x" + walletAddress);

            succed.setKeyStore(keyStore);
            EventBus.getDefault().post(succed);
        } catch (CipherException e) {
            CreateETHAdressSucced succed = new CreateETHAdressSucced();
            succed.setState(2);
            EventBus.getDefault().post(succed);
            e.printStackTrace();
        } catch (IOException e) {
            CreateETHAdressSucced succed = new CreateETHAdressSucced();
            succed.setState(3);
            EventBus.getDefault().post(succed);
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            CreateETHAdressSucced succed = new CreateETHAdressSucced();
            succed.setState(3);
            EventBus.getDefault().post(succed);
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            CreateETHAdressSucced succed = new CreateETHAdressSucced();
            succed.setState(3);
            EventBus.getDefault().post(succed);
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            CreateETHAdressSucced succed = new CreateETHAdressSucced();
            succed.setState(3);
            EventBus.getDefault().post(succed);
            e.printStackTrace();
        }
    }

//    private void sendNotification() {
//        builder = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setColor(0x2d435c)
//                .setTicker(normalMode ? getString(R.string.notification_wallgen_title) : getString(R.string.notification_wallimp_title))
//                .setContentTitle(this.getResources().getString(normalMode ? R.string.wallet_gen_service_title : R.string.wallet_gen_service_title_import))
//                .setOngoing(true)
//                .setProgress(0, 0, true)
//                .setContentText(getString(R.string.notification_wallgen_maytake));
//        final NotificationManager mNotifyMgr =
//                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//
//        mNotifyMgr.notify(mNotificationId, builder.build());
//    }
//
//    private void finished(String address) {
//        builder
//                .setContentTitle(normalMode ? getString(R.string.notification_wallgen_finished) : getString(R.string.notification_wallimp_finished))
//                .setLargeIcon(Blockies.createIcon(address.toLowerCase()))
//                .setAutoCancel(true)
//                .setLights(Color.CYAN, 3000, 3000)
//                .setSound(android.provider.Settings.System.DEFAULT_NOTIFICATION_URI)
//                .setProgress(100, 100, false)
//                .setOngoing(false)
//                .setAutoCancel(true)
//                .setContentText(getString(R.string.notification_click_to_view));
//
//        if (android.os.Build.VERSION.SDK_INT >= 18) // Android bug in 4.2, just disable it for everyone then...
//            builder.setVibrate(new long[]{1000, 1000});
//
//        Intent main = new Intent(this, MainActivity.class);
//        main.putExtra("STARTAT", 1);
//
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
//                main, PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(contentIntent);
//
//        final NotificationManager mNotifyMgr =
//                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//
//        mNotifyMgr.notify(mNotificationId, builder.build());
//    }


}
