import styled from "styled-components";
import Header from "../Header/Header";

/*
    Irá mostrar o conteúdo da página
*/
function Layout({children}){
    return(
        <WrapperLayout>
            <Header />
            {children}
        </WrapperLayout>
    );
}

export default Layout;

const WrapperLayout = styled.section`
    margin: 16px;
`;