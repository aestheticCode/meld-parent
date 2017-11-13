import { ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AppHome } from './app.home';

const appRoutes: Routes = [
    { path: '', component: AppHome },
    { path: 'channel', loadChildren: 'app/channel/channel.module#ChannelModule' },
    { path: 'usercontrol', loadChildren: 'app/usercontrol/usercontrol.module#UserControlModule' },
    { path: 'social', loadChildren: 'app/social/social.module#SocialModule' }
];
export const appRoutingProviders: any[] = [];
export const routing: ModuleWithProviders = RouterModule.forRoot(appRoutes);
