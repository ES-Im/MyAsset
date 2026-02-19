import { Link } from "react-router";
import google from '@/assets/images/login/google.png';
const GoogleLogin = ({
  height
}) => {
  return <>
      <Link to="/" >
          <div>
            <img src={google} alt="logo" height={height ?? 45} />
          </div>
      </Link>
    </>;
};
export default GoogleLogin;