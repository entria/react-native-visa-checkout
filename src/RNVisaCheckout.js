
import { NativeModules } from 'react-native';

const { RNVisaCheckout } = NativeModules;

export const configureProfileAsync = async (environment, apiKey, profileName) => {
  const data = await RNVisaCheckout.configureProfile(environment, apiKey, profileName);
  return data;
};

export const checkoutAsync = async (total, currency) => {
  const data = await RNVisaCheckout.checkout(total, currency);
  return data;
};

export const Constants = {
  ConfigStatus: RNVisaCheckout.ConfigStatus,
  ResultStatus: RNVisaCheckout.ResultStatus,
  Environment: RNVisaCheckout.Environment,
  Country: RNVisaCheckout.Country,
  Currency: RNVisaCheckout.Currency,
  Card: RNVisaCheckout.Card,
};
