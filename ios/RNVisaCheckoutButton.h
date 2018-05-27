//
//  RNVisaCheckoutButton.h
//  RNVisaCheckout
//
//  Created by Joao Guilherme Daros Fidelis on 07/02/18.
//  Copyright © 2018 Facebook. All rights reserved.
//

#import <UIKit/UIKit.h>

@class VisaProfile;

@interface RNVisaCheckoutButton : UIView

@property (nonatomic, assign) NSInteger cardStyle;
@property (nonatomic, assign) BOOL cardAnimations;

- (instancetype)initWithProfile:(VisaProfile *)profile;
- (void)setCardStyle:(NSInteger)cardStyle;
- (void)setCardAnimations:(BOOL)cardAnimations;
- (void)updateCheckoutOptions:(NSDictionary *)options;

@end
