# react-native-z-view

Show a view on top of all the views (including native modals). It can be used like an overlay view.

## Installation

```sh
npm install react-native-z-view react-native-screens
```

> Note: react-native-screens is required for iOS.

## Usage

```jsx
import { ZView } from 'react-native-z-view'

<ZView>
  <View>
    <Text>This will show on top of all the views!<Text>
  </View>
</ZView>
```

## Props

- `top` - To adjust top value. Similar to `top` in position fixed. Accepts percentage and point values.
- `left` - To adjust left value. Similar to `left` in position fixed. Accepts percentage and point values.
- `bottom` - To adjust bottom value. Similar to `bottom` in position fixed. Accepts percentage and point values.
- `right` - To adjust right value. Similar to `right` in position fixed. Accepts percentage and point values.

## Examples

### Full Size Overlay

```jsx
import { Dimensions } from 'react-native'

const { width, height } = Dimensions.get('window')

<ZView>
  <View
    style={{
      width,
      height,
    }}
  >
    <Text>Full size overlay view</Text>
  </View>
</ZView>
```

## Why?

- React Native's Modal is great for modal usecases. It blocks the touch of behind views (which is expected from a Modal), so it is not a great solution for custom Toast, ToolTip or Popover that allow behind view touches.
- Multiple Modals don't work unless nested on iOS in react native. I have made a [PR](https://github.com/facebook/react-native/pull/31498) for the same.
- This component solves the above issues but it is not a replacement for RN's Modal component. Use this component when you face above issues.
- This component makes sure to appear on top of Native Modal on Android and iOS so it can be used in place of a custom Portal like solution.

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT

---

Made with [create-react-native-library](https://github.com/callstack/react-native-builder-bob)
