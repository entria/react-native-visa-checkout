import React from 'react';
import PropTypes from 'prop-types';
import { NativeModules, findNodeHandle, requireNativeComponent, ViewPropTypes } from 'react-native';

const { RNVisaCheckout } = NativeModules;

export default class CheckoutButton extends React.Component {
  static Constants = {
    Environment: RNVisaCheckout.Environment,
    Country: RNVisaCheckout.Country,
    Currency: RNVisaCheckout.Currency,
    Card: RNVisaCheckout.Card,
  };

  static propTypes = {
    ...ViewPropTypes,
    cardStyle: PropTypes.number,
    cardAnimations: PropTypes.bool,
    onCardCheckout: PropTypes.func,
    onCardCheckoutError: PropTypes.func,
    checkoutOptions: PropTypes.object,
  };

  static defaultProps: Object = {
    cardStyle: 1,
    cardAnimations: true,
    checkoutOptions: { total: 0.0, currency: 0 },
  };

  _onCardCheckout = (event) => {
    if (this.props.onCardCheckout) {
      this.props.onCardCheckout(event.nativeEvent);
    }
  }

  _onCardCheckoutError = (event) => {
    if (this.props.onCardCheckoutError) {
      this.props.onCardCheckoutError(event.nativeEvent);
    }
  }

  _setReference = (ref) => {
    if (ref) {
      this._buttonRef = ref;
      this._buttonHandle = findNodeHandle(ref);
    } else {
      this._buttonRef = null;
      this._buttonHandle = null;
    }
  };

  async componentWillMount() {
    // const data = await RNVisaCheckout.configureProfile(RNVisaCheckout.Environment.Sandbox, 'apiKey', null);
  }

  render() {
    return (
      <RNVisaCheckoutButton
        {...this.props}
        ref={this._setReference}
        onCardCheckout={this._onCardCheckout}
        onCardCheckoutError={this._onCardCheckoutError}
      />
    );
  }
}

const RNVisaCheckoutButton = requireNativeComponent('RNVisaCheckoutButton', CheckoutButton);
