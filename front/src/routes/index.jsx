import { lazy } from 'react';
import { Navigate } from 'react-router';
import MainLayout from '@/layouts/MainLayout';

// Dashboards
const Dashboard = lazy(() => import('@/views/dashboards/dashboard'));

// Auth
const AuthSignIn = lazy(() => import('@/views/auth/sign-in'));
const AuthDeleteAccount = lazy(() => import('@/views/auth/delete-account'));
const AuthCallback = lazy(() => import('@/components/auth/AuthCallback'));
// Landing
// const Landing = lazy(() => import('@/views/landing'));


// Error
// const Error400 = lazy(() => import('@/views/error/400'));
// const Error401 = lazy(() => import('@/views/error/401'));
// const Error403 = lazy(() => import('@/views/error/403'));
// const Error404 = lazy(() => import('@/views/error/404'));
// const Error408 = lazy(() => import('@/views/error/408'));
// const Error500 = lazy(() => import('@/views/error/500'));
// const Maintenance = lazy(() => import('@/views/other-pages/maintenance'));


const authRoutes = [
{
    path: '/auth/sign-in',
    element: <AuthSignIn />
}, {
    path: '/auth/delete-account',
    element: <AuthDeleteAccount />
}, {
    path: '/auth/callback',
    element: <AuthCallback />
}
];
const errorRoutes = [
// {
//   path: '/error/400',
//   element: <Error400 />
// }, {
//   path: '/error/401',
//   element: <Error401 />
// }, {
//   path: '/error/403',
//   element: <Error403 />
// }, {
//   path: '/error/404',
//   element: <Error404 />
// }, {
//   path: '/error/408',
//   element: <Error408 />
// }, {
//   path: '/error/500',
//   element: <Error500 />
// }
];

const otherPagesRoutes = [
// {
//   path: '/coming-soon',
//   element: <ComingSoon />
// }, {
//   path: '/maintenance',
//   element: <Maintenance />
// }
];

const dashboardRoutes = [{
  path: '/dashboard',
  element: <Dashboard />
}
];

const landingRoute = [
// {
//   path: '/landing',
//   element: <Landing />
// }
];



const allRoutes = [{
  element: <MainLayout />,
  children: [{
    path: '/',
    element: <Navigate to="/dashboard" replace />
  }, ...dashboardRoutes, ]
}];
const otherRoutes = [...authRoutes, ...errorRoutes, ...landingRoute, ...otherPagesRoutes];
export const routes = [...allRoutes, ...otherRoutes];