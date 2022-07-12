import styled from 'styled-components';
import useGitHub from '../GitHubHooks/GitHubHooks';

// mostra todos os dados do usuário, incluindo repositórios, recebidos da API do GitHub
function Profile(){

    const {gitHubState} = useGitHub();

    return(
        <Wrapper>
            <PanelLeft>
            <WrapperImg src={gitHubState.user.avatar} alt='avatar do usuário no GitHub' />
            <WrapperStatusCount>
                <div>
                    <h4>Followers</h4>
                    <span>{gitHubState.user.followers}</span>
                </div>
                <div>
                    <h4>Following</h4>
                    <span>{gitHubState.user.following}</span>
                </div>
                <div>
                    <h4>Repos</h4>
                    <span>{gitHubState.user.publicRepos}</span>
                </div>
            </WrapperStatusCount>
            </PanelLeft>
            <WrapperUserInfo>
                <div>
                    {gitHubState.user.bio && 
                        <WrapperUserBio>{gitHubState.user.bio}</WrapperUserBio>
                    }
                    <h1>{gitHubState.user.name}</h1> 
                    <WrapperUserGeneric>
                        <h3>Usuário: </h3>
                        <a href={gitHubState.user.publicURL} target='_blank' rel='noreferrer'>{gitHubState.user.login}</a>
                    </WrapperUserGeneric>
                    {gitHubState.user.company && 
                        <WrapperUserGeneric>
                            <h3>Empresa: </h3>
                            <span>{gitHubState.user.company}</span>
                        </WrapperUserGeneric>
                    }
                    {gitHubState.user.location &&
                        <WrapperUserGeneric>
                            <h3>Localização: </h3>
                            <span>{gitHubState.user.location}</span>
                        </WrapperUserGeneric>
                    }
                    {gitHubState.user.blogURL &&
                        <WrapperUserGeneric>
                            <h3><a href={gitHubState.user.blogURL} target='_blank' rel='noreferrer'>Blog/Website</a></h3>
                        </WrapperUserGeneric>
                    }
                </div>                
            </WrapperUserInfo>
        </Wrapper>
    )
}

export default Profile;

const Wrapper = styled.div`
    display: flex;
`;

const PanelLeft = styled.div`
    display: flex;
    flex-direction: column;
    align-items: center;
`;

const WrapperImg = styled.img`
    border-radius: 50%;
    width: 200px;
    height: 200px;
    margin: 8px;
`;

const WrapperStatusCount = styled.div`
    display: flex;

    div{
        margin: 8px;
        text-align: center;
    }

    h4 {
        font-size: 16px;
        font-weight: bold;
    }
`;

const WrapperUserInfo = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    margin-left: 8px;

    h1 {
        font-size: 32px;
        font-weight: bold;
        margin-left: 20px;
    }
`;

const WrapperUserBio = styled.div`
    border: 3px solid #CCC;
    border-radius: 10px;
    padding: 10px 20px;
    text-align: justify;
    margin-bottom: 20px;
    width: 400px;
    position:relative;

    // desenho para a "flechinha" simulando um balão de diálogo 
    &::before{
        content: '';
        position: absolute;
        bottom: 7px;
        left: -15px;
        border-left: 15px solid transparent;
        border-bottom: 27px solid #ccc;
        width: 0;
        height: 0;
    }
`;

const WrapperUserGeneric = styled.div`
    display: flex;
    align-items: flex-end;
    margin: 8px 0 0 20px;

    h3 {
        margin-right: 8px;
        font-size: 18px;
        font-weight: bold;
    }

    a {
        color: blue;
        font-size: 18px;
        font-weight: bold;
    }
`;