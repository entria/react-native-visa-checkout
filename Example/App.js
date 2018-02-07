import React from 'react';
import { StyleSheet, View } from 'react-native';
import { RNVisaCheckoutButton } from 'react-native-visa-checkout';

export default class CameraScreen extends React.Component {

  render() {
    return (<View style={styles.container}>
              <RNVisaCheckoutButton style={styles.navigation} />
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
