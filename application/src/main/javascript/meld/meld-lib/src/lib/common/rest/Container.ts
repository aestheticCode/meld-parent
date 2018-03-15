import {Column} from "./Column";
import {LinksContainer} from "./LinksContainer";
import {Link} from "./Link";

export interface Container<D> extends LinksContainer{

  columns : Column[];

  rows : D[];

  size : number;

}

export class ContainerModel<D> implements Container<D> {
  columns: Column[] = [];
  rows: D[];
  size: number;
  links: Link[] = [];

}
