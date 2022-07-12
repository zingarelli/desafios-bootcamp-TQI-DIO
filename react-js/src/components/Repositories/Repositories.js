import { useEffect, useState } from 'react';
import { Tabs, TabList, Tab, TabPanel } from 'react-tabs';
import styled from 'styled-components';
import useGitHub from '../GitHubHooks/GitHubHooks';
import Repository from '../Repository/Repository';

/*
    Monta os repositórios do usuário, a serem exibidos na página
*/
function Repositories(){
    const {gitHubState, getUserRepos, getUserStars} = useGitHub();

    //controla o que mostrar baseado no que retornou da busca por usuário
    const [hasSearchResults, setHasSearchResults] = useState(false); 

    useEffect(() => {
        //se a busca retornar dados
        if(gitHubState.user.login) {
            getUserRepos(gitHubState.user.login); //está dando warning, mas o instrutor não resolveu e eu não entendi
            getUserStars(gitHubState.user.login); //está dando warning, mas o instrutor não resolveu e eu não entendi
            setHasSearchResults(true);
        }
        else {
            setHasSearchResults(false);
        }
    }, [gitHubState.user.login]);


    return (
        {hasSearchResults} && ( 
            <WrapperTabs 
            selectedTabClassName='is-selected'
            selectedTabPanelClassName='is-selected'
            >
                <WrapperTabList>
                    <WrapperTab>Repositórios</WrapperTab>
                    <WrapperTab>Stars</WrapperTab>
                </WrapperTabList>
                <WrapperTabPanel>
                    {gitHubState.repositories.map((item) => (
                        <Repository 
                            key={item.id}
                            name={item.name} 
                            linkToRepo={item.html_url} 
                            description={item.description} 
                        />
                    ))}                    
                </WrapperTabPanel> 
                <WrapperTabPanel>
                {gitHubState.starred.map((item) => (
                        <Repository 
                            key={item.id}
                            name={item.name} 
                            linkToRepo={item.html_url} 
                            description={item.description} 
                        />
                    ))}  
                </WrapperTabPanel> 
            </WrapperTabs>
        )        
    );
};

export default Repositories;

const WrapperTabs = styled(Tabs)`
    font-size: 16px;
    width: 100%;
    margin-top: 16px;
`;

const WrapperTabList = styled(TabList)`
    list-style-type: none;
    padding: 4px;
    display: flex;
    margin: 0;
`;

WrapperTabList.tabsRole = 'TabList'; // wrapper para especificar que esse componente estilizado ainda é um tablist

const WrapperTab = styled(Tab)`
    border-radius: 16px;
    border: 1px solid #CCC;
    padding: 16px;
    margin: 8px;
    user-select: none;
    cursor: pointer;

    &:focus{
        outline:none; /* sem estilo adicional ao clicar em uma aba */
    }

    &.is-selected{
        box-shadow: 3px 2px 10px rgba(0, 0, 0, 0.2);
    }
`;

WrapperTab.tabsRole = 'Tab';

const WrapperTabPanel = styled(TabPanel)`
    display: none;
    min-height: 40vh; 
    padding: 16px;
    margin-top: -5px;


    &.is-selected{
        display: flex;
        flex-wrap: wrap;
        justify-content: space-around;
    }
`;

WrapperTabPanel.tabsRole = 'TabPanel';

