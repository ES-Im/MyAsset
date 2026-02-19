import { TbApps, TbBasket, TbBellRinging, TbCalendar, TbComponents, TbCreditCard, TbFolder, TbHeadset, TbLayoutDashboard, TbLock, TbLogout2, TbMessageDots, TbSettings2, TbUserCircle, TbUserHexagon, TbUsers } from 'react-icons/tb';
import {
    LuCalendar,
    LuChartNoAxesCombined,
    LuCircleGauge,
    LuDessert,
    LuEarth,
    LuEyeOff,
    LuFileInput,
    LuFingerprint,
    LuFireExtinguisher,
    LuFolderOpenDot,
    LuHandshake,
    LuHousePlug,
    LuInbox,
    LuKey,
    LuLifeBuoy,
    LuListTree,
    LuMapPinned,
    LuMessageSquareDot,
    LuNotebookText,
    LuPanelRightClose,
    LuPanelTop,
    LuPencilRuler,
    LuProportions,
    LuReceiptText,
    LuRss,
    LuShieldAlert,
    LuShieldBan,
    LuShoppingBag,
    LuSparkles,
    LuTable,
    LuUsers
} from 'react-icons/lu';

export const userDropdownItems = [{
  label: 'Welcome back!',
  isHeader: true
}, {
  label: 'Profile',
  icon: TbUserCircle,
  url: '/users/profile'
}, {
  label: 'Log Out',
  icon: TbLogout2,
  url: '#',
  class: 'text-danger fw-semibold'
}];

export const menuItems = [{
  key: 'navigation',
  label: 'Navigation',
}, {
  key: 'Login',
  label: '로그인',
  url: '/auth/sign-in',
  icon: LuFingerprint,
}, {
    key: 'Reactivate',
    label: '계정활성화',
    url: '/auth/delete-account',
    icon: LuFireExtinguisher,
}, {
  key: 'dashboards',
  label: 'Dashboards',
  icon: LuCircleGauge,
  url: '/dashboard'
}, {
    key: 'items',
    label: 'Menu Items',
    isTitle: true
}

];