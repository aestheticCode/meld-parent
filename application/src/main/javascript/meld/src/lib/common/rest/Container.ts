import {Column} from "./Column";
import {LinksContainer} from "./LinksContainer";

export interface Container<D> extends LinksContainer{

  columns : Column[];

  rows : D[];

  size : number;

}
