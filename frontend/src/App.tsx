import { Route, Routes } from "react-router-dom";
import { AuthGate } from "./auth";
import { AppShell } from "./components/AppShell";
import CategoriasPage from "./pages/CategoriasPage";
import ProdutosPage from "./pages/ProdutosPage";
import ClientesPage from "./pages/ClientesPage";
import VendasPage from "./pages/VendasPage";
import ItemVendasPage from "./pages/ItemVendasPage";
import IncioPage from "./pages/IncioPage";

export default function App() {
  return (
    <AuthGate>
      <AppShell brand={`Padaria`} links={[{"label":"Início","to":"/"},{"label":"Categorias","to":"/categorias"},{"label":"Produtos","to":"/produtos"},{"label":"Clientes","to":"/clientes"},{"label":"Vendas","to":"/vendas"},{"label":"ItemVendas","to":"/itens-venda"}]} layout="sidebar">
        <Routes>
          <Route path="/categorias" element={<CategoriasPage />} />
          <Route path="/produtos" element={<ProdutosPage />} />
          <Route path="/clientes" element={<ClientesPage />} />
          <Route path="/vendas" element={<VendasPage />} />
          <Route path="/itens-venda" element={<ItemVendasPage />} />
          <Route path="/" element={<IncioPage />} />
        </Routes>
      </AppShell>
    </AuthGate>
  );
}
