import { DataTable, ApiForm, PageBreadcrumb } from "../components";
import { Plus, X } from "lucide-react";
import { useState } from "react";
import { createPortal } from "react-dom";
import { produtosService } from "../services/produtos.service";
import { categoriasService } from "../services/categorias.service";

export default function ProdutosPage() {
  const [createOpen, setCreateOpen] = useState(false);
  const [reloadToken, setReloadToken] = useState(0);
  const closeCreateDialog = () => setCreateOpen(false);


  return (
    <div className="page">

      <PageBreadcrumb items={[{ label: `Listagem de Produtos` }]} />

      <header className="page-header">
        <div>
          <h1 className="page-title">{`Listagem de Produtos`}</h1>
        </div>
      </header>
      <p style={{ margin: "-18px 0 20px 0" }}>{`Gerencie produtos cadastrados, revise status e execute ações recorrentes.`}</p>
      <div className="page-actions">
        <button className="btn btn-primary" type="button" onClick={() => setCreateOpen(true)}>
          <Plus size={16} aria-hidden="true" />
          Adicionar
        </button>
      </div>
      <DataTable load={produtosService.list} reloadToken={reloadToken} columns={[{"key":"id","label":"id"},{"key":"categoriaId","label":"categoriaId"},{"key":"nome","label":"nome"},{"key":"descricao","label":"descricao"},{"key":"preco","label":"preco"},{"key":"quantidade_estoque","label":"quantidade_estoque"},{"key":"ativo","label":"ativo"}]} idKey={"id"} fields={[{"name":"categoriaId","type":"number","required":true,"readOnly":false,"relation":{"endpoint":"/categorias","valueKey":"id","labelKey":"nome"}},{"name":"nome","type":"text","required":true,"readOnly":false},{"name":"descricao","type":"textarea","required":false,"readOnly":false},{"name":"preco","type":"number","required":true,"readOnly":false},{"name":"quantidade_estoque","type":"number","required":true,"readOnly":false},{"name":"ativo","type":"checkbox","required":true,"readOnly":false}]} relationLoaders={{ "categoriaId": categoriasService.list }} onUpdate={produtosService.update} onDelete={produtosService.remove} />
      {createOpen && createPortal(
        <div className="dialog-overlay" role="presentation" onClick={closeCreateDialog}>
          <section
            className="dialog-content shadcn-dialog"
            role="dialog"
            aria-modal="true"
            aria-labelledby="create-dialog-title"
            onClick={(event) => event.stopPropagation()}
          >
            <header className="dialog-header">
              <div>
                <h2 id="create-dialog-title" className="dialog-title">Adicionar</h2>
                <p className="dialog-description">Preencha os dados para criar um novo registro.</p>
              </div>
              <button className="icon-btn" type="button" onClick={closeCreateDialog} aria-label="Fechar">
                <X size={16} aria-hidden="true" />
              </button>
            </header>
            <ApiForm
              submit={produtosService.create}
              fields={[{"name":"categoriaId","type":"number","required":true,"readOnly":false,"relation":{"endpoint":"/categorias","valueKey":"id","labelKey":"nome"}},{"name":"nome","type":"text","required":true,"readOnly":false},{"name":"descricao","type":"textarea","required":false,"readOnly":false},{"name":"preco","type":"number","required":true,"readOnly":false},{"name":"quantidade_estoque","type":"number","required":true,"readOnly":false},{"name":"ativo","type":"checkbox","required":true,"readOnly":false}]}
              submitLabel="Adicionar"
              relationLoaders={{ "categoriaId": categoriasService.list }}
              onSuccess={() => {
                setCreateOpen(false);
                setReloadToken((value) => value + 1);
              }}
            />
          </section>
        </div>,
        document.body
      )}
    </div>
  );
}
