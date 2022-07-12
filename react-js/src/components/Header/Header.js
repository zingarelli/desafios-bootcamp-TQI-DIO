/*
    O cabeçalho da aplicação é onde ficará a barra para pesquisar um usuário,
    cujos dados de GitHub serão então exibidos na tela
*/
import { useState } from "react";
import styled from "styled-components";
import useGitHub from "../GitHubHooks/GitHubHooks";

function Header(){
    const { getUser } = useGitHub();
    const [usernameForSearch, setUsernameForSearch] = useState();

    const submitGetUser = () => {
        if(usernameForSearch) 
            return getUser(usernameForSearch);
    }

    return(
        <Wrapper>
            <input 
                type="text" 
                placeholder="Digite o nome do usuário para a pesquisa..." 
                onChange={(e) => setUsernameForSearch(e.target.value)}
            />
            <button type="submit" onClick={submitGetUser}>Buscar</button>
        </Wrapper>
    )
}

export const Wrapper = styled.div`
    display: flex;
    width: 100%;
    justify-content: space-between; 
    align-items: center;
    padding: 4px;
    margin-bottom: 20px;

    input{
        border: 1px solid #CCC;
        border-radius: 8px;
        width: 100%;
        height: 44px;
        padding: 8px;
    }

    button{
        background-color: #225ED8;
        padding: 10px 20px;
        margin: 0 8px;
        border-radius: 8px;
        font-weight: bold;
        font-size: 14px;
        color: #FFF;

        &:hover{
            background-color: #2C5282;
            box-shadow: 3px 2px 10px rgba(0, 0, 0, 0.2);
        }
    }
`;


export default Header;