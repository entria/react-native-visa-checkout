/*!
 @header TrustDefender.h

 TrustDefender Mobile SDK for iOS. This header is the main framework header, and is required to make use of the mobile SDK.

 @author Nick Blievers
 @copyright ThreatMetrix
 */
#ifndef _TRUSTDEFENDERMOBILE_H_
#define _TRUSTDEFENDERMOBILE_H_

@import Foundation;
@import CoreLocation;

#import "THMStatusCode.h"

#ifdef __cplusplus
#define EXTERN		extern "C" __attribute__((visibility ("default")))
#else
#define EXTERN	    extern __attribute__((visibility ("default")))
#endif

#define THM_NAME_PASTE2( a, b) a##b
#define THM_NAME_PASTE( a, b) THM_NAME_PASTE2( a, b)

#ifndef THM_PREFIX_NAME
#define NO_COMPAT_CLASS_NAME
#define THM_PREFIX_NAME Visa
#endif

#define THMTrustDefender                THM_NAME_PASTE(THM_PREFIX_NAME, THMTrustDefender)

/*
 * For this to work, all exported symbols must be included here
 */
#ifdef THM_PREFIX_NAME
#define THMOrgID                        THM_NAME_PASTE(THM_PREFIX_NAME, THMOrgID)
#define THMApiKey                       THM_NAME_PASTE(THM_PREFIX_NAME, THMApiKey)
#define THMDelegate                     THM_NAME_PASTE(THM_PREFIX_NAME, THMDelegate)
#define THMTimeout                      THM_NAME_PASTE(THM_PREFIX_NAME, THMTimeout)
#define THMLocationServices             THM_NAME_PASTE(THM_PREFIX_NAME, THMLocationServices)
#define THMLocationServicesWithPrompt   THM_NAME_PASTE(THM_PREFIX_NAME, THMLocationServicesWithPrompt)
#define THMDesiredLocationAccuracy      THM_NAME_PASTE(THM_PREFIX_NAME, THMDesiredLocationAccuracy)
#define THMKeychainAccessGroup          THM_NAME_PASTE(THM_PREFIX_NAME, THMKeychainAccessGroup)
#define THMOptions                      THM_NAME_PASTE(THM_PREFIX_NAME, THMOptions)
#define THMEnableOptions                THM_NAME_PASTE(THM_PREFIX_NAME, THMEnableOptions)
#define THMDisableOptions               THM_NAME_PASTE(THM_PREFIX_NAME, THMDisableOptions)
#define THMFingerprintServer            THM_NAME_PASTE(THM_PREFIX_NAME, THMFingerprintServer)
#define THMProfileSourceURL             THM_NAME_PASTE(THM_PREFIX_NAME, THMProfileSourceURL)
#define THMSessionID                    THM_NAME_PASTE(THM_PREFIX_NAME, THMSessionID)
#define THMCustomAttributes             THM_NAME_PASTE(THM_PREFIX_NAME, THMCustomAttributes)
#define THMLocation                     THM_NAME_PASTE(THM_PREFIX_NAME, THMLocation)
#define THMProfileStatus                THM_NAME_PASTE(THM_PREFIX_NAME, THMProfileStatus)
#endif

// Instance wide options
/*!
 @const THMOrgID
 @abstract NSDictionary key for specifying the org id.
 @discussion Valid at init time to set the org id.

 This is mandatory.
 */
EXTERN NSString *const THMOrgID;
/*!
 @const THMApiKey
 @abstract NSDictionary key for specifying the API key, if one is required.
 @discussion Valid at init time to set the API key. Do not set unless instructed by
 Threatmetrix.
 */
EXTERN NSString *const THMApiKey;
/*!
 @const THMDelegate
 @abstract NSDictionary key for specifying the delegate.
 @discussion Valid at init time to set the delegate, which must comply to
 TrustDefenderMobileDelegate
 */
EXTERN NSString *const THMDelegate;
/*!
 @const THMTimeout
 @abstract NSDictionary key for specifying the network timeout.
 @discussion Valid at init time to set the network timeout, defaults to 10s

 Default is \@10 (note use of NSNumber to store int)
 */
EXTERN NSString *const THMTimeout;
/*!
 @const THMLocationServices
 @abstract NSDictionary key for enabling the location services.
 @discussion Valid at init time to enable location services. Note that
 this will never cause UI interaction -- if the application does not have
 permissions, no prompt will be made, and no location will be acquired.

 Default value is \@NO (note use of NSNumber to store BOOL)
 */
EXTERN NSString *const THMLocationServices;
/*!
 @const THMLocationServicesWithPrompt
 @abstract NSDictionary key for enabling the location services.
 @discussion Valid at init time to enable location services. Note that
 this can cause user interaction -- if the application does not have
 permissions, they will be prompted.

 Only one of THMLocationServices or THMLocationServicesWithPrompt should be set.

 Default value is \@NO (note use of NSNumber to store BOOL)
 */
EXTERN NSString *const THMLocationServicesWithPrompt;

/*!
 @const THMDesiredLocationAccuracy
 @abstract NSDictionary key for enabling the location services.
 @discussion Valid at init time and configures the desired location accuracy.

 Default value is \@1000.0 (note use of NSNumber to store float) which is
 equivilent to kCLLocationAccuracyKilometer
 */
EXTERN NSString *const THMDesiredLocationAccuracy;

/*!
 @const THMKeychainAccessGroup
 @abstract NSDictionary key for making use of the keychain access group.
 @discussion Valid at init time to enable the sharing of data across applications
 with the same keychain access group. This allows matching device ID across applications
 from the same vendor.
 */
EXTERN NSString *const THMKeychainAccessGroup;

/*!
 @const THMOptions
 @abstract NSDictionary key for setting specific options
 @discussion Valid at init time for fine grained control over profiling.

 Used internally. Do not set unless specified by ThreatMetrix.
 */
EXTERN NSString *const THMOptions;

/*!
 @const THMEnableOption
 @abstract NSDictionary key for setting specific options
 @discussion Valid at init time for fine grained control over profiling.

 Used internally. Do not set unless specified by ThreatMetrix.
 */
EXTERN NSString *const THMEnableOptions;

/*!
 @const THMDisableOptions
 @abstract NSDictionary key for setting specific options
 @discussion Valid at init time for fine grained control over profiling.

 Used internally. Do not set unless specified by ThreatMetrix.
 */
EXTERN NSString *const THMDisableOptions;
/*!
 @const THMFingerprintServer
 @abstract NSDictionary key for setting a fingerprint server
 @discussion Valid at init time setting an alternative fingerprint server

 Defaults to \@"h-sdk.online-metrix.net"
 */
EXTERN NSString *const THMFingerprintServer;
/*!
 @const THMProfileSourceURL
 @abstract NSDictionary key for setting a custom url.
 @discussion Valid at init time for setting a custom referrer url
 */
EXTERN NSString *const THMProfileSourceURL;

// Profile specific options.
/*!
 @const THMSessionID
 @abstract NSDictionary key for Session ID.
 @discussion Valid at profile time, and result time for setting/retrieving the session ID.
 */
EXTERN NSString *const THMSessionID;
/*!
 @const THMCustomAttributes
 @abstract NSDictionary key for Custom Attributes.
 @discussion Valid at profile time for setting the any custom attributes to be included
 in the profiling data.
 */
EXTERN NSString *const THMCustomAttributes;
/*!
 @const THMLocation
 @abstract NSDictionary key for setting location.
 @discussion Valid at profile time for setting the location to be included
 in the profiling data.

 This should only be used if location services are not enabled.
 */
EXTERN NSString *const THMLocation;

// Profile result options (THMSessionID is shared)

/*!
 @const THMProfileStatus
 @abstract NSDictionary key for retrieving the profiling status
 @discussion Valid at results time for getting the status of the current
 profiling request.
 */
EXTERN NSString *const THMProfileStatus;


// NOTE: headerdoc2html gets confused if this __attribute__ is after the comment
__attribute__((visibility("default")))
/*!
 *    @interface TrustDefenderMobile
 *    @discussion TrustDefender Mobile SDK
 */
@interface THMTrustDefender : NSObject

-(instancetype) init NS_UNAVAILABLE;
+(instancetype) allocWithZone:(struct _NSZone *)zone NS_UNAVAILABLE;
+(instancetype) new NS_UNAVAILABLE;

/*!
 * Initialise a shared instance of TrustDefenderMobile object.
 * @code
 * TrustDefenderMobile *THM = [TrustDefenderMobile sharedInstance];
 * @endcode
 *
 */
+(instancetype) sharedInstance;

/*!
 * Configure the shared instance of TrustDefenderMobile object with the supplied configuration dictionary.
 * @code
 * [THM configure:@{ THMOrgID: @"my orgid" }];
 * @endcode
 *
 * @return The result of the configuration call.
 *
 * @remark This method only run once and any following calls to it has no effect.
 */
-(BOOL) configure:(NSDictionary *)config;

/*!
 *    Perform a profiling request.
 *
 *    @return THMStatusCode indicating whether the profiling request successfully launched
 */
-(THMStatusCode) doProfileRequest;

/*!
 *    Perform a profiling request.
 *
 *    @return THMStatusCode indicating whether the profiling request successfully launched
 */
-(THMStatusCode) doProfileRequest: (NSDictionary *)options;

/*!
 *     Perform a profiling request.
 *     callbackBlock is a block interface that is fired when the profiling request is completed.
 *     Note that if a block is passed in, the delegate callback will not be fired.
 */
-(THMStatusCode) doProfileRequestWithCallback: (void (^)(NSDictionary *))callbackBlock;

/*!
 *     Perform a profiling request.
 *     callbackBlock is a block interface that is fired when the profiling request is completed.
 *     Note that if a block is passed in, the delegate callback will not be fired.
 */
-(THMStatusCode) doProfileRequestWithOptions: (NSDictionary *)profileOptions andCallbackBlock: (void (^)(NSDictionary *))callbackBlock;

/*!
 *    Pause or resume location services
 *
 *    @param pause YES to pause, NO to unpause
 */
-(void) pauseLocationServices: (BOOL) pause;

/*!
 * Return the profiling result details
 */
-(NSDictionary *)getResult;

/*!
 *    Query the build number, for debugging purposes only
 */
-(NSString *)version;

/*!
 *    Cancel's the currently running sync request. If no request is outstanding, just returns
 */
-(void) cancel;

@end

/*!
 *    The delegate should implement this protocol to receive completion notification. Only one of the
 *    methods should be implemented.
 */
@protocol THMTrustDefenderDelegate
/*!
 *    Once profiling is complete, this method is called.
 *
 *    @param profileResults describes the profiling status
 */
-(void) profileComplete: (NSDictionary *) profileResults;

@end
#endif
