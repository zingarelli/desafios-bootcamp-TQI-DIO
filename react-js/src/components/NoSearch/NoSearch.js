import styled from "styled-components";

// será mostrado quando a página carrega e nenhuma busca ainda foi feita
function NoSearch(){
    return(
        <Wrapper>Digite um nome de usuário na busca para mostrar os dados do GitHub.</Wrapper>
    );
};

export const Wrapper = styled.p`
    margin-left: 4px;    
`;

export default NoSearch;