import * as React from 'react';
import type { ZViewProps } from './type';

import { requireNativeComponent, UIManager, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-z-view' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

const ComponentName = 'ZView';

const ZViewImpl =
  UIManager.getViewManagerConfig(ComponentName) != null
    ? requireNativeComponent<any>(ComponentName)
    : () => {
        throw new Error(LINKING_ERROR);
      };

export let ZViewRootViewGroup =
  requireNativeComponent<any>('ZViewRootViewGroup');

const absoluteStyle = {
  position: 'absolute',
};

export const ZView = (props: ZViewProps) => {
  const { children, touchable, top, left, bottom, right } = props;

  const coordinates = React.useMemo(
    () => ({
      top,
      left,
      right,
      bottom,
    }),
    [top, left, bottom, right]
  );

  return (
    <ZViewImpl
      style={absoluteStyle}
      pointerEvents="none"
      touchable={touchable}
      coordinates={coordinates}
    >
      <ZViewRootViewGroup>{children}</ZViewRootViewGroup>
    </ZViewImpl>
  );
};
