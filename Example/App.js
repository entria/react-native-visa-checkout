import React from 'react';
import { StyleSheet, View } from 'react-native';
import { RNVisaCheckoutButton } from 'react-native-visa-checkout';

export default class CameraScreen extends React.Component {

  onCardCheckout = (event) => {
    console.log('onCardCheckout');
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
    height: 30,
    width: 300,
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
  },
});
