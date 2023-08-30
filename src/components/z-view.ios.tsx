import { FullWindowOverlay } from 'react-native-screens';
import * as React from 'react';
import { View, useWindowDimensions } from 'react-native';
import type { ZViewProps } from './type';

export const ZView = (props: ZViewProps) => {
  const { left, top, children, bottom, right, touchable } = props;
  const { height, width } = useWindowDimensions();
  const containerViewStyle = React.useMemo(() => {
    return {
      position: 'absolute',
      width,
      height,
      alignItems: 'flex-start',
    } as const;
  }, [width, height]);

  const innerContainerStyle = React.useMemo(() => {
    return {
      position: 'absolute',
      top,
      left,
      bottom,
      right,
    } as const;
  }, [top, left, bottom, right]);

  return (
    // @ts-ignore
    <FullWindowOverlay>
      <View
        style={containerViewStyle}
        pointerEvents={touchable === false ? 'none' : 'box-none'}
      >
        <View style={innerContainerStyle} pointerEvents="box-none">
          {children}
        </View>
      </View>
    </FullWindowOverlay>
  );
};
