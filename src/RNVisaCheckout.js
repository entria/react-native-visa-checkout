// @flow
import { NativeModules } from 'react-native';

const { RNVisaCheckout } = NativeModules;

export const configureProfile = (environment: number, apiKey: string, profileName: string) => {
  RNVisaCheckout.configureProfile(environment, apiKey, profileName);
};

type CheckoutResponse = {
  callId: string,
  lastFourDigits: string,
  country: number,
  postalCode: string,
  paymentMethodType: string,
  cardBrand: number,
  encryptedKey: string,
};
export const checkoutAsync = async (total: number, currency: number): Promise<CheckoutResponse> => {
  return await RNVisaCheckout.checkout(total, currency);
};

export const Constants = {
  Environment: RNVisaCheckout.Environment,
  Country: RNVisaCheckout.Country,
  Currency: RNVisaCheckout.Currency,
  Card: RNVisaCheckout.Card,
  CardStyle: RNVisaCheckout.CardStyle,
};
