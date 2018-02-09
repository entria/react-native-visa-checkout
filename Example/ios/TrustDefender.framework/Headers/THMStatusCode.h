/*!
 @header THMStatusCode.h

 The statuses that are used as indicators of profiling state.

 @author Nick Blievers
 @copyright ThreatMetrix
 */

#ifndef TrustDefenderMobile_THMStatusCode_h
#define TrustDefenderMobile_THMStatusCode_h
@import Foundation;

/*!
 @typedef THMStatusCode

 Possible return codes
 @constant THMStatusCodeNotYet                   profile request has returned but not yet completed
 @constant THMStatusCodeOk                       Completed, No errors
 @constant THMStatusCodeConnectionError          There has been a connection issue, profiling incomplete
 @constant THMStatusCodeHostNotFoundError        Unable to resolve the host name of the fingerprint server
 @constant THMStatusCodeNetworkTimeoutError      Network timed out
 @constant THMStatusCodeHostVerificationError    Certificate verification or other SSL failure! Potential Man In The Middle attack"
 @constant THMStatusCodeInternalError            Internal Error, profiling incomplete or interrupted
 @constant THMStatusCodeInterruptedError         Request was cancelled
 @constant THMStatusCodePartialProfile           Connection error, only partial profile completed
 @constant THMStatusCodeInvalidOrgID             Request contained an invalid org id
 */
typedef NS_ENUM(NSInteger, THMStatusCode)
{
    THMStatusCodeNotYet = 0,
    THMStatusCodeOk,
    THMStatusCodeConnectionError,
    THMStatusCodeHostNotFoundError,
    THMStatusCodeNetworkTimeoutError,
    THMStatusCodeHostVerificationError,
    THMStatusCodeInternalError,
    THMStatusCodeInterruptedError,
    THMStatusCodePartialProfile,
    THMStatusCodeInvalidOrgID,
    THMStatusCodeNotConfigured,
};


#endif
