
package org.reactnative.visacheckout;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

import java.io.File;

public class RNVisaCheckoutModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  public RNVisaCheckoutModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNVisaCheckout";
  }

  @ReactMethod
  public void configureProfile(final int environment, final String apiKey, final String profileName final Promise promise) {
//    final ReactApplicationContext context = getReactApplicationContext();
//    final File cacheDirectory = mScopedContext.getCacheDirectory();
//    UIManagerModule uiManager = context.getNativeModule(UIManagerModule.class);
//    uiManager.addUIBlock(new UIBlock() {
//      @Override
//      public void execute(NativeViewHierarchyManager nativeViewHierarchyManager) {
//        RNCameraView cameraView = (RNCameraView) nativeViewHierarchyManager.resolveView(viewTag);
//        try {
//          if (!Build.FINGERPRINT.contains("generic")) {
//            if (cameraView.isCameraOpened()) {
//              cameraView.takePicture(options, promise, cacheDirectory);
//            } else {
//              promise.reject("E_CAMERA_UNAVAILABLE", "Camera is not running");
//            }
//          } else {
//            Bitmap image = RNCameraViewHelper.generateSimulatorPhoto(cameraView.getWidth(), cameraView.getHeight());
//            ByteBuffer byteBuffer = ByteBuffer.allocate(image.getRowBytes() * image.getHeight());
//            image.copyPixelsToBuffer(byteBuffer);
//            new ResolveTakenPictureAsyncTask(byteBuffer.array(), promise, options).execute();
//          }
//        } catch (Exception e) {
//          promise.reject("E_CAMERA_BAD_VIEWTAG", "takePictureAsync: Expected a Camera component");
//        }
//      }
//    });
  }
}