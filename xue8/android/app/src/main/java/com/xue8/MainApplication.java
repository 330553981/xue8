package com.xue8;

import android.app.Application;
import android.util.Log;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

public class MainApplication extends Application implements ReactApplication {
  private static final String JSBUNDLE_FILE = "ReactNativeDevBundle.js";

  @Override
  public void onCreate() {
    super.onCreate();

    new Thread(){
      @Override
      public void run() {
        prepareJSBundle();
        super.run();
      }
    }.start();
  }

  private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
    @Override
    protected boolean getUseDeveloperSupport() {
      return BuildConfig.DEBUG;
    }

    @Override
    protected List<ReactPackage> getPackages() {
      return Arrays.<ReactPackage>asList(
          new MainReactPackage()
      );
    }
  };

  private static void copyFile(InputStream in, OutputStream out) throws IOException {
    byte[] buffer = new byte[1024];
    int read;
    while ((read = in.read(buffer)) != -1) {
      out.write(buffer, 0, read);
    }
  }

  private void prepareJSBundle() {
    File targetFile = new File(getFilesDir(), JSBUNDLE_FILE);
    if (!targetFile.exists()) {
      try {
        OutputStream out = new FileOutputStream(targetFile);
        InputStream in = getAssets().open(JSBUNDLE_FILE);

        copyFile(in, out);
        out.close();
        in.close();
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public ReactNativeHost getReactNativeHost() {
      return mReactNativeHost;
  }
}
