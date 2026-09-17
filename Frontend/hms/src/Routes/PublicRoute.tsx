import { useSelector } from "react-redux"
import { Navigate } from "react-router-dom"
import type { JSX } from "react/jsx-runtime"
import { jwtDecode } from "jwt-decode";

interface PublicRoutesProps {
    children: JSX.Element
}

const PublicRoute: React.FC<PublicRoutesProps> = ({ children }) => {

    const token = useSelector((state: any) => state.jwt)

    if (token) {
        const user: any = jwtDecode(token);

        console.log(user?.role);

        return <Navigate to= {`/${user?.role?.toLowerCase()}/dashboard `} />;
    }
            return children;
 }

            export default PublicRoute