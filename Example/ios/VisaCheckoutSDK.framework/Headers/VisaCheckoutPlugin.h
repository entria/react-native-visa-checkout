/**
 Copyright © 2018 Visa. All rights reserved.
 */

#import <UIKit/UIKit.h>
@import WebKit.WKScriptMessageHandler;

/**
 VisaCheckoutPlugin is used to configure Visa Checkout for hybrid environments. 
 VisaCheckoutPlugin conforms to the WKScriptMessageHandler protocol and
 will act as a message handler for your instance of WKWebView.
 */
@interface VisaCheckoutPlugin : NSObject <WKScriptMessageHandler>

/// :nodoc:
- (instancetype _Nonnull)init NS_UNAVAILABLE;

/**
 Singleton instance of VisaCheckoutPlugin that is used to configure 
 Visa Checkout for hybrid environments. Use this instance when adding
 message handlers to your instance of WKWebView.
 */
@property (class, nonatomic, readonly) VisaCheckoutPlugin * _Nonnull main;

/**
 Provide an instance of UIViewController that will be used to present Visa Checkout
 modally. When the Visa Checkout button is clicked by the user, VisaCheckoutPlugin
 will use this view controller to call present(_:animated:completion:). 
 
 This property is required to launch Visa Checkout. The UIViewController instance 
 must be in your view hierarchy and must not already have a presentingViewController 
 set because any additional calls to present(_:animated:completion:) will be 
 ignored by UIKit.
 
 Typically, you will set this value to the view controller that contains your 
 webView.
 */
@property (nonatomic, weak) UIViewController * _Nullable presentingViewController;

/**
 You can use this method as a one line integration for configuring Visa Checkout
 for hybrid environments. You can call this method passing an instance of WKWebView
 that will eventually render the Visa Checkout button. Also pass in an instance
 of UIViewController that is able to be used in a call to present(_:animated:completion:).

 @param contentController an instance of the webView's userContentController, usually
 accessed in this manner: myWkWebView.configuration.userContentController
 
 @param viewController an instance of UIViewController that will be used to present Visa Checkout
 modally. When the Visa Checkout button is clicked by the user, VisaCheckoutPlugin
 will use this view controller to call present(_:animated:completion:).
 */
+ (void)configure:(WKUserContentController *_Nonnull)contentController
   viewController:(UIViewController *_Nonnull)viewController;

/**
 Only used for UIWebView support (use configure:contentController:viewController if using WKWebView).

 Configure your app with Visa Checkout for hybrid environments using a UIWebView.

 @param webView the UIWebView that will be loading the page with the Visa Checkout button.

 @param viewController an instance of UIViewController that will be used to present Visa Checkout
 modally. When the Visa Checkout button is clicked by the user, VisaCheckoutPlugin
 will use this view controller to call present(_:animated:completion:).
 */
+ (void)configureWithWebView:(UIWebView *_Nonnull)webView viewController:(UIViewController *_Nonnull)viewController;

/**
 Only used for UIWebView support (not needed if WKWebView used).

 Used in webView:shouldStartLoadWithRequest:navigationType: UIWebViewDelegate method to
 determine if the request should be handled by Visa Checkout.

 @param request the request to check against (forwarded from webView:shouldStartLoadWithRequest:navigationType: UIWebViewDelegate method).
 */
+ (BOOL)shouldHandleRequest:(NSURLRequest *_Nonnull)request;

/**
 Only used for UIWebView support (not needed if WKWebView used).

 If a request from the webView:shouldStartLoadWithRequest:navigationType: UIWebViewDelegate method
 is found to need to be handled by Visa Checkout (as a result of calling VisaCheckoutPlugin.shouldHandleRequest),
 forward the request to this method for handling by Visa Checkout.

 @param request the request forwarded from the webView:shouldStartLoadWithRequest:navigationType: UIWebViewDelegate method.
 */
+ (void)handleRequest:(NSURLRequest *_Nonnull)request;

@end
