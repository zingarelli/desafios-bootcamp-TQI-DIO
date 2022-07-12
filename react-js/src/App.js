import Footer from "./components/Footer/Footer";
import useGitHub from "./components/GitHubHooks/GitHubHooks";
import Layout from "./components/Layout/Layout";
import NoSearch from "./components/NoSearch/NoSearch";
import Profile from "./components/Profile/Profile";
import Repositories from "./components/Repositories/Repositories";

/* 
  Desafio ReactJS - Bootcamp TQI|DIO
  Instrutor: Matheus Benites (https://github.com/benits)
  Aplicação feita em React que consome dados da API do GitHub para criar uma página personalizada,
  em que é possível buscar um usuário de GitHub e mostrar na tela dados de perfil deste usuário, 
  bem como a lista de repositórios, tanto criadas quanto "starred".
  API do GitHub: https://docs.github.com/en/rest
  Esta aplicação utiliza as seguintes bibliotecas:
  - Axios para fazer e tratar requisições HTTP: npm i axios;
  - Styled Components para utilizar CSS nos arquivos JSX: npm install --save styled-components
  - react-tabs para criar abas (tabs) para navegar entra os repositórios e os "starred": npm install --save react-tabs
*/
function App() {
  //para verificar se um usuário já foi buscado e se seus dados já foram carregados
  const { gitHubState } = useGitHub();
  
  return (
      //sim, essa parte está muito feia >(
      <Layout>
        {gitHubState.hasUser ? ( // um usuário foi buscado
          <>
          {gitHubState.user.login === null ? ( // usuário não existe
            <p>Usuário não encontrado</p>
          ) : (
            <>
            {gitHubState.loading ? ( //mostra um aviso de loading enquanto não carrega os dados
              <p>Loading...</p>
            ) : (
              <>
              <Profile />
              <Repositories />
              </>
            )}
            </>
          )}
          </>
        ) : (
          <NoSearch />
        )}
      <Footer />   
      </Layout>
  );
}

export default App;
