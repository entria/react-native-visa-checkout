import React from 'react';
import { StyleSheet, View } from 'react-native';
import { RNVisaCheckoutButton, RNVisaCheckout } from 'react-native-visa-checkout';

export default class ExampleScreen extends React.Component {

  onCardCheckout = (event) => {
    console.log('onCardCheckout');
    console.log('event: ', event);
    console.log(`callId: ${event.callId}`);
  }

  onCardCheckoutError = ({ code, message }) => {
    console.log('onCardCheckoutError');
    console.log(`Error with code: ${code}, message: ${message}`);
  }

  componentWillMount() {
    console.log('will mount!2');
    // RNVisaCheckout.configureProfile(1, 'a', 'default');
    RNVisaCheckout.configureProfileAsync(RNVisaCheckout.Constants.Environment.Sandbox, 'D07PAGIDFKP5P0D7H18E13sk_kJpEPSmjh3BJReRwIt2VyaUI', 'default');
    console.log('eita');
  }

  render() {
    const checkoutOption = { total: 22.09, currency: RNVisaCheckoutButton.Constants.Currency.USD };
    return (<View style={styles.container}>
              <RNVisaCheckoutButton 
                style={styles.navigation} 
                checkoutOptions={checkoutOption} 
                onCardCheckout={this.onCardCheckout} 
                onCardCheckoutError={this.onCardCheckoutError} />
            </View>);
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    paddingTop: 100,
    backgroundColor: '#000',
  },
  navigation: {
    height: 50,
    width: 300,
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
  },
});
