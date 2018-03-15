import {Enum} from '@aestheticcode/meld-lib';

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
