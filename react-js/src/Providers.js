// Infelizmente, não entendi por que o instrutor fez assim (nem como isso deu certo...) :(

import App from "./App";
import { ResetCSS } from "./global/ResetCSS";
import GitHubProvider from "./providers/GitHubProvider";

function Providers(){
    return (
        <main>
            <GitHubProvider>
                <ResetCSS />
                <App />
            </GitHubProvider>
        </main>
    );
};

export default Providers;