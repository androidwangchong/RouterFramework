package com.base.app.ethprotocol.services;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;


import com.base.app.ethprotocol.data.FullWallet;
import com.base.app.ethprotocol.utils.AddressNameConverter;
import com.base.app.ethprotocol.utils.OwnWalletUtils;
import com.base.app.ethprotocol.utils.Settings;
import com.base.app.ethprotocol.utils.WalletStorage;

import org.spongycastle.util.encoders.Hex;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.ECKeyPair;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;




/**
 * decentralized-exchange-app-android
 * Created by wangchong on 2018/5/10.
 */

public class WalletGenPrivateKeyService extends IntentService {

    private NotificationCompat.Builder builder;
    final int mNotificationId = 152;

    private boolean normalMode = true;

    public WalletGenPrivateKeyService() {
        super("WalletGen Service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String password = intent.getStringExtra("PASSWORD");
        String privatekey = "";

        if (intent.hasExtra("PRIVATE_KEY")) {
            normalMode = false;
            privatekey = intent.getStringExtra("PRIVATE_KEY");
        }

        try {
            String walletAddress;
            if (normalMode) { // Create new key
                walletAddress = OwnWalletUtils.generateNewWalletFile(password, new File(this.getFilesDir(), ""), true);
            } else { // Privatekey passed
                ECKeyPair keys = ECKeyPair.create(Hex.decode(privatekey));
                walletAddress = OwnWalletUtils.generateWalletFile(password, keys, new File(this.getFilesDir(), ""), true);
            }

            WalletStorage.getInstance(this).add(new FullWallet("0x" + walletAddress, walletAddress), this);
            AddressNameConverter.getInstance(this).put("0x" + walletAddress, "Wallet " + ("0x" + walletAddress).substring(0, 6), this);
            Settings.walletBeingGenerated = false;

        } catch (CipherException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
    }



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