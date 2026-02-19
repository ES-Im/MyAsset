import { Link } from "react-router";
import naver from '@/assets/images/login/naver.png';
const NaverLogin = ({
  height
}) => {
  return <>
      <Link to="/" >
          <div>
             <img src={naver} alt="logo" height={height ?? 45} />
          </div>
      </Link>
    </>;
};
export default NaverLogin;