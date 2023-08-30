export type ZViewProps = {
  top?: number | `${number}%`;
  bottom?: number | `${number}%`;
  left?: number | `${number}%`;
  right?: number | `${number}%`;
  children?: React.ReactNode;
  touchable?: boolean;
};
