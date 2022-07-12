import styled from "styled-components";

/*
    Monta um card com alguns dados do repositório disponível do usuário, retornado pela API do GitHub
*/
function Repository({name, linkToRepo, description}){
    return (
        <Wrapper>
            <h2>{name}</h2>
            <h4>{description}</h4>
            <a href={linkToRepo} target="_blank" rel="noreferrer">Ver repositório</a>
        </Wrapper>
    );
};

export const Wrapper = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    border: 1px solid #CCC;
    border-radius: 8px;
    margin: 16px;
    padding: 8px;
    width: 340px;
    min-height: 150px;

    h2 {
        font-size: 22px;
        font-weight: bold;
        margin-bottom: 20px;
        text-align: center;
    }

    h4 {
        font-size: 16px;
        color: #2D3748;
        margin-bottom: 8px;
    }

    a {
        color: #3182CE;
    }
`;

export default Repository;