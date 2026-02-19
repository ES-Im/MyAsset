import { Link } from "react-router";
import kakao from '@/assets/images/login/kakao.png';
const KakaoLogin = ({
  height
}) => {
  return <>
      <Link to="/" >
          <div>
            <img src={kakao} alt="logo" height={height ?? 45} />
          </div>
      </Link>
    </>;
};
export default KakaoLogin;