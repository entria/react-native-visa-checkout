// @flow
import React from 'react';
import PropTypes from 'prop-types';
import {
  NativeModules,
  findNodeHandle,
  requireNativeComponent,
  ViewPropTypes,
  Platform,
} from 'react-native';

const { RNVisaCheckout } = NativeModules;

type CheckoutOptions = {
  total: number,
  currency: number,
};

type Props = ViewPropTypes & {
  cardStyle?: number,
  cardAnimations?: boolean,
  onCardCheckout?: Function,
  onCardCheckoutError?: Function,
  checkoutOptions?: CheckoutOptions,
  apiKey: string,
  environment: number,
  profileName: string,
};

export default class CheckoutButton extends React.Component<Props> {
  static Constants = {
    Environment: RNVisaCheckout.Environment,
    Country: RNVisaCheckout.Country,
    Currency: RNVisaCheckout.Currency,
    Card: RNVisaCheckout.Card,
    CardStyle: RNVisaCheckout.CardStyle,
  };

  static propTypes = {
    ...ViewPropTypes,
    cardStyle: PropTypes.number,
    cardAnimations: PropTypes.bool,
    onCardCheckout: PropTypes.func,
    onCardCheckoutError: PropTypes.func,
    checkoutOptions: PropTypes.object,
    apiKey: PropTypes.string,
    environment: PropTypes.number,
    profileName: PropTypes.string,
  };

  static defaultProps: Object = {
    cardStyle: RNVisaCheckout.CardStyle.Standard,
    cardAnimations: true,
    checkoutOptions: { total: 0.0, currency: 0 },
    apiKey: '',
    environment: RNVisaCheckout.Environment.Sandbox,
    profileName: 'default',
  };

  _onCardCheckout = event => {
    if (this.props.onCardCheckout) {
      this.props.onCardCheckout(event.nativeEvent);
    }
  };

  _onCardCheckoutError = event => {
    if (this.props.onCardCheckoutError) {
      this.props.onCardCheckoutError(event.nativeEvent);
    }
  };

  _setReference = ref => {
    if (ref) {
      this._buttonRef = ref;
      this._buttonHandle = findNodeHandle(ref);
    } else {
      this._buttonRef = null;
      this._buttonHandle = null;
    }
  };

  componentWillMount() {
    const { apiKey, profileName, environment } = this.props;
    RNVisaCheckout.configureProfile(environment, apiKey, profileName);
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

const nativeComponentName = Platform.OS === 'android' ? 'RNVisaCheckoutButton' : 'RNVisaCheckout';

const RNVisaCheckoutButton = requireNativeComponent(nativeComponentName, CheckoutButton);
