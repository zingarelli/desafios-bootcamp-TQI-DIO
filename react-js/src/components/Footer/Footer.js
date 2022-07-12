import styled from "styled-components";

function Footer(){
    return (
        <WrapFooter>
            Projeto originalmente criado por <a href="https://github.com/benits/github-api-interface" target="_blank" rel="noreferrer">Matheus Benites</a> e modificado por <a href="https://github.com/zingarelli" target="_blank" rel="noreferrer">Matheus Zingarelli</a>. Bootcamp TQI|DIO.
        </WrapFooter>
    );
};

const WrapFooter = styled.footer`
    height: 3vh;
    font-size: 12px;
    text-align: center;
    border-top: 1px solid #CCC;
    padding: 5px 0;
    margin-top: 30px;

    a {
        color: blue;
        font-weight: bold;
    }
`;

export default Footer;