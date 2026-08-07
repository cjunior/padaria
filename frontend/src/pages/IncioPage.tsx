import { Hero, ResourceOverview, PageBreadcrumb } from "../components";
import { Link } from "react-router-dom";
import { categoriasService } from "../services/categorias.service";
import { produtosService } from "../services/produtos.service";
import { clientesService } from "../services/clientes.service";
import { vendasService } from "../services/vendas.service";
import { itensvendaService } from "../services/itensvenda.service";

export default function IncioPage() {

  return (
    <div className="page">

      <PageBreadcrumb items={[{ label: `Padaria` }]} />

      <Hero title={`Bem-vindo ao sistema de gestão da padaria`} subtitle={`Gerencie categorias, produtos, clientes e vendas de forma simples e segura.`} />
      <div className="container-block">
        <Link className="btn btn-primary" to="/login">{`Entrar`}</Link>
        <Link className="btn btn-secondary" to="/register">{`Criar meu perfil`}</Link>
      </div>
      <ResourceOverview resources={[{ label: "Categorias", to: "/categorias", load: categoriasService.list }, { label: "Produtos", to: "/produtos", load: produtosService.list }, { label: "Clientes", to: "/clientes", load: clientesService.list }, { label: "Vendas", to: "/vendas", load: vendasService.list }, { label: "ItemVendas", to: "/itens-venda", load: itensvendaService.list }]} />
    </div>
  );
}
