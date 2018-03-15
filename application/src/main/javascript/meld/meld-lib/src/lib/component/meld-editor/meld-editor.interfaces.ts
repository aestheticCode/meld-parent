export interface EditorCommand {

  trigger : RegExp;

  execute(element: HTMLElement, regexResult: String);

}
