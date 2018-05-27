/**
 Copyright © 2018 Visa. All rights reserved.
 */

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
#import "VisaPurchaseInfo.h"
#import "VisaProfile.h"
#import "VisaCheckoutResult.h"

typedef NS_ENUM(NSInteger, VisaCheckoutButtonStyle);

/**
 This is the main point of interaction for your customers. The user
 will tap this button in order to initiate the Visa Checkout SDK's
 user interface. A VisaCheckoutButton can have a `VisaCheckoutButtonStyle`
 for different visual rendering options.
 
 You can use the `onCheckout(profile:purchaseInfo:presenting:completion:)` method to provide information that
 is used when the button is tapped by the user.
 */
@interface VisaCheckoutButton : UIView

/**
 The `CheckoutButtonStyle` to use for rendering. This is used to
 contrast with lighter or darker background superviews.
 */
@property (nonatomic, assign) VisaCheckoutButtonStyle style;

- (void)onCheckoutWithPurchaseInfo:(VisaPurchaseInfo * _Nonnull)purchaseInfo
                      completion:(VisaCheckoutResultHandler _Nonnull)completion
DEPRECATED_MSG_ATTRIBUTE("Please use onCheckout(profile:purchaseInfo:presenting:completion:) instead")
NS_SWIFT_NAME(onCheckout(purchaseInfo:completion:));

/**
 A method to set the purchase information, presenting view controller and the completion handler for Visa Checkout.
 For the presenting view controller, provide an instance of UIViewController that will be used to present Visa Checkout modally. When the Visa Checkout button is clicked by a user, VisaCheckout
 will use this view controller to call present(\_:animated:completion:).
 This property is required to launch Visa Checkout. The UIViewController instance
 must be in your view hierarchy and must not already have a presentingViewController
 set because any additional calls to present(\_:animated:completion:) will be
 ignored by UIKit.
 Typically, you will set this value to the view controller that contains your
 VisaCheckoutButton.
 
 @param purchaseInfo The purchase information with various settings used to customize the Checkout experience.
 @param presentingViewController Instance of UIViewController that will be used to present Visa Checkout
 modally.
 @param completion A completion handler that is called when `VisaCheckout` is finished and
 has return context back to your app.
 */
- (void)onCheckoutWithProfile:(VisaProfile *_Nonnull)profile
                 purchaseInfo:(VisaPurchaseInfo *_Nonnull)purchaseInfo
     presentingViewController:(UIViewController *_Nonnull)presentingViewController
                   completion:(VisaCheckoutResultHandler _Nullable)completion
NS_SWIFT_NAME(onCheckout(profile:purchaseInfo:presenting:completion:));

- (void)onCheckoutWithTotal:(VisaCurrencyAmount * _Nonnull)total
                   currency:(VisaCurrency)currency
                 completion:(void (^ _Nonnull)(VisaCheckoutResult * _Nonnull))completion
DEPRECATED_MSG_ATTRIBUTE("Please use onCheckout(profile:purchaseInfo:presenting:completion:) instead")
NS_SWIFT_NAME(onCheckout(total:currency:completion:));

/**
 This returns card art for recognized returning users. Otherwise, this returns the mini button image.
 
 ![miniButtonImage](../img/mini.png)
 */
+ (UIImage * _Nonnull)miniButtonImage;

/** A value indicating whether the Visa Checkout SDK is configured and ready to launch,
 The VisaCheckoutButton will be enabled when this property is true (and disabled when this property is false).
 */
@property (nonatomic, readonly) BOOL isReady;

/** Convenience property for Interface Builder to set the value of VisaCheckoutButtonStyleStandard.
 Default value is true.
 */
@property (nonatomic) IBInspectable BOOL standardStyle;

/** The enableAnimation is used to turn on or off the animation on the button.
 */
@property (nonatomic) BOOL enableAnimation DEPRECATED_MSG_ATTRIBUTE("Might not work as expected");

@end

/**
 The style to be used for rendering a `VisaCheckoutButton` instance.
 */
typedef NS_ENUM(NSInteger, VisaCheckoutButtonStyle) {
    /// <img src="../img/neutral.png" title="Neutral Button" alt="Neutral Button">
    VisaCheckoutButtonStyleNeutral,
    /// <img src="../img/standard.png" title="Standard Button" alt="Standard Button">
    VisaCheckoutButtonStyleStandard
} NS_SWIFT_NAME(CheckoutButtonStyle);
