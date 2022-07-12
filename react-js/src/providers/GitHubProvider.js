/*
    Consome a API do GitHub e disponibiliza os dados para a aplicação por meio do ContextAPI
*/
import { createContext, useCallback, useState } from 'react';
import api from '../services/api'

// cria o contexto com os dados que serão compartilhados para a aplicação
export const GitHubContext = createContext({
    hasUser: false, //usado para verificar se um username foi inserido na busca
    loading: false, //usado para enviar uma mensagem antes e depois de carregar os dados
    user: {},
    repositories: [],
    starred: [],
})

function GitHubProvider({children}){
    //dados que estamos interessados em consumir da API do GitHub
    const [gitHubState, setGitHubState] = useState({
        hasUser: false,
        loading: false,
        user: {
            login: undefined,
            name: undefined,
            avatar: undefined,
            publicURL: undefined,
            blogURL: undefined,
            company: undefined,
            location: undefined,
            followers: 0,
            following: 0,
            publicGists: 0,
            publicRepos: 0,
            bio: undefined,
        },
        repositories: [],
        starred: [],
    });

    //faz a busca pelo usuário na API do GitHub
    const getUser = (username) => {
        //dados estão sendo carregados...
        setGitHubState((prevState) => ({
            ...prevState,
            loading: !prevState.loading //vira true
        }));
        api.get(`users/${username}`).then(({data}) => {
            setGitHubState((prevState) => ({
                ...prevState, 
                hasUser: true, //busca foi realizada
                user: {
                    login: data.login,
                    name: data.name,
                    avatar: data.avatar_url,
                    publicURL: data.html_url,
                    blogURL: data.blog,
                    company: data.company,
                    location: data.location,
                    followers: data.followers,
                    following: data.following,
                    publicGists: data.public_gists,
                    publicRepos: data.public_repos,
                    bio: data.bio,
                }
            }))
        })
        .catch((e) => { // tratamento para usuário não encontrado, infelizmente ainda gera erro no console...
            if(e.response.status === 404) {
                setGitHubState((prevState) => ({
                    ...prevState,
                    user: {login: null},
                }));
            };
        })
        .finally(() => {
            //dados já foram carregados
            setGitHubState((prevState) => ({
                ...prevState,
                loading: !prevState.loading //volta para false
            }));
        });
    };

    // faz a busca no GitHub pelos repositórios do usuário
    const getUserRepos = (username) => {
        api.get(`users/${username}/repos`).then(({data}) => {
            setGitHubState((prevState) => ({
                ...prevState,
                repositories: data
            }));
        });
    };

    // faz a busca no GitHub do usuário pelos repositórios com star 
    const getUserStars = (username) => {
        api.get(`users/${username}/starred`).then(({data}) => {
            setGitHubState((prevState) => ({
                ...prevState,
                starred: data
            }));
        });
    };

    //funções que a aplicação terá acesso
    const contextValue = {
        gitHubState,
        //hooks para que as funções sejam memoizadas e criadas somente na construção do componente (array vazio)
        getUser: useCallback((username) => getUser(username), []),
        getUserRepos: useCallback((username) => getUserRepos(username), []), 
        getUserStars: useCallback((username) => getUserStars(username), []), 
    }

    return (
        <GitHubContext.Provider value={contextValue}>{children}</GitHubContext.Provider>
    );
};

export default GitHubProvider;