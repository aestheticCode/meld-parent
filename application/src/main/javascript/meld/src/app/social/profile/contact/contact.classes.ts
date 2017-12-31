import {Enum} from 'lib/pipe/meld-enum/meld-enum.interfaces';

export function mobileTypes() : Enum[] {
  return [
    {
      value: 'HOME',
      label: 'Home'
    },
    {
      value: 'MOBILE',
      label: 'Mobile'
    },
    {
      value: 'WORK',
      label: 'Work'
    }
  ];

}
