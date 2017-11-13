import {EditorAction} from "./EditorAction";
import {EditorCallback} from "./EditorCallback";

export abstract class EditorCommand {

  public action : EditorAction = EditorAction.DEACTIVATE;

  constructor(public trigger: RegExp) {}

  abstract dispatch(action : EditorAction, context?: string, callback?: EditorCallback)

}
