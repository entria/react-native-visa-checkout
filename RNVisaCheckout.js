
// import { requestPermissions } from './handlePermissions';

import { NativeModules } from 'react-native';

const { RNVisaCheckout } = NativeModules;

export const configureProfileAsync = (environment, apiKey, profileName, callback) => {
  RNVisaCheckout.configureProfile(environment, apiKey, profileName, callback);
}

export const checkoutAsync = async (total, currency) => {
  return await RNVisaCheckout.checkoutAsync(total, currency);
}

export Constants = { 
  ConfigStatus: RNVisaCheckout.ConfigStatus,
  ResultStatus: RNVisaCheckout.ResultStatus,
  Environment: RNVisaCheckout.Environment
 }
