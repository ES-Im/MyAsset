import { TbApps, TbBasket, TbBellRinging, TbCalendar, TbComponents, TbCreditCard, TbFolder, TbHeadset, TbLayoutDashboard, TbLock, TbLogout2, TbMessageDots, TbSettings2, TbUserCircle, TbUserHexagon, TbUsers } from 'react-icons/tb';
import { LuCalendar, LuChartNoAxesCombined, LuCircleGauge, LuDessert, LuEarth, LuEyeOff, LuFileInput, LuFingerprint, LuFolderOpenDot, LuHandshake, LuHousePlug, LuInbox, LuKey, LuLifeBuoy, LuListTree, LuMapPinned, LuMessageSquareDot, LuNotebookText, LuPanelRightClose, LuPanelTop, LuPencilRuler, LuProportions, LuReceiptText, LuRss, LuShieldAlert, LuShieldBan, LuShoppingBag, LuSparkles, LuTable, LuUsers } from 'react-icons/lu';

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
  key: 'dashboards',
  label: 'Dashboards',
  icon: LuCircleGauge,
  url: '/dashboard'
}, {
  key: 'items',
  label: 'Menu Items',
  isTitle: true
}, {
  key: 'menu-levels',
  label: 'Menu Levels',
  icon: LuListTree,
  children: [{
    key: 'second-level',
    label: 'Second Level',
    children: [{
      key: 'item-2-1',
      label: 'Item 2.1',
      url: ''
    }, {
      key: 'item-2-2',
      label: 'Item 2.2',
      url: ''
    }]
  }, {
    key: 'third-level',
    label: 'Third Level',
    children: [{
      key: 'item-3-1',
      label: 'Item 1',
      url: ''
    }, {
      key: 'fourth-level',
      label: 'Item 2',
      children: [{
        key: 'item-4-1',
        label: 'Item 3.1',
        url: ''
      }, {
        key: 'item-4-2',
        label: 'Item 3.2',
        url: ''
      }]
    }]
  }]
}, {
  key: 'disabled-menu',
  label: 'Disabled Menu',
  icon: LuEyeOff,
  url: '#!',
  isDisabled: true
}];