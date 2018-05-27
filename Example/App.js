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

  render() {
    const checkoutOption = { total: 22.09, currency: RNVisaCheckoutButton.Constants.Currency.USD };
    return (<View style={styles.container}>
              <RNVisaCheckoutButton 
                apiKey={'PUT_API_KEY_HERE'}
                environment={RNVisaCheckout.Constants.Environment.Sandbox}
                profileName={'default'}
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
