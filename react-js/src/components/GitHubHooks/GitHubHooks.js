/*
    Centraliza todos os hooks da aplicação que envolvem a API do GitHub
*/
import { useContext } from "react";
import { GitHubContext } from "../../providers/GitHubProvider";

const useGitHub = () => {
    const {gitHubState, getUser, getUserRepos, getUserStars} = useContext(GitHubContext);

    return {gitHubState, getUser, getUserRepos, getUserStars};
}

export default useGitHub;