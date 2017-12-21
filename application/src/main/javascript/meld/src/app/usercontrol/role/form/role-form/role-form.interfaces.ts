import {LinksContainer} from '../../../../../lib/common/rest/LinksContainer';

export interface RoleForm extends LinksContainer {

  id: string;

  name: string;

  permissions: string[]

}

export interface PermissionRow {
  id: string
  name: string
  path: string
  method: string
  created: string
}
