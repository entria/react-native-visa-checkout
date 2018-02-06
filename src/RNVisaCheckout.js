// @flow
import { NativeModules } from 'react-native';

const { RNVisaCheckout } = NativeModules;

type ConfigureResponse = {
  status: number,
};
export const configureProfileAsync = async (environment: number, apiKey: string, profileName: string): Promise<ConfigureResponse> => {
  return await RNVisaCheckout.configureProfile(environment, apiKey, profileName);
};

type CheckoutResponse = {
  callId: string,
  lastFourDigits: string,
  country: number,
  postalCode: string,
  paymentMethodType: string,
  cardBrand: number,
  encryptedKey: string,
}
export const checkoutAsync = async (total: string, currency: number): Promise<CheckoutResponse> => {
  return await RNVisaCheckout.checkout(total, currency);
};

export const Constants = {
  ConfigStatus: RNVisaCheckout.ConfigStatus,
  ResultStatus: RNVisaCheckout.ResultStatus,
  Environment: RNVisaCheckout.Environment,
  Country: RNVisaCheckout.Country,
  Currency: RNVisaCheckout.Currency,
  Card: RNVisaCheckout.Card,
};
