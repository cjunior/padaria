import { DataTable, ApiForm, PageBreadcrumb } from "../components";
import { Plus, X } from "lucide-react";
import { useState } from "react";
import { createPortal } from "react-dom";
import { categoriasService } from "../services/categorias.service";

export default function CategoriasPage() {
  const [createOpen, setCreateOpen] = useState(false);
  const [reloadToken, setReloadToken] = useState(0);
  const closeCreateDialog = () => setCreateOpen(false);


  return (
    <div className="page">

      <PageBreadcrumb items={[{ label: `Listagem de Categorias` }]} />

      <header className="page-header">
        <div>
          <h1 className="page-title">{`Listagem de Categorias`}</h1>
        </div>
      </header>
      <p style={{ margin: "-18px 0 20px 0" }}>{`Gerencie categorias cadastrados, revise status e execute ações recorrentes.`}</p>
      <div className="page-actions">
        <button className="btn btn-primary" type="button" onClick={() => setCreateOpen(true)}>
          <Plus size={16} aria-hidden="true" />
          Adicionar
        </button>
      </div>
      <DataTable load={categoriasService.list} reloadToken={reloadToken} columns={[{"key":"id","label":"id"},{"key":"nome","label":"nome"},{"key":"descricao","label":"descricao"},{"key":"ativo","label":"ativo"}]} idKey={"id"} fields={[{"name":"nome","type":"text","required":true,"readOnly":false},{"name":"descricao","type":"textarea","required":false,"readOnly":false},{"name":"ativo","type":"checkbox","required":true,"readOnly":false}]} onUpdate={categoriasService.update} onDelete={categoriasService.remove} />
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
              submit={categoriasService.create}
              fields={[{"name":"nome","type":"text","required":true,"readOnly":false},{"name":"descricao","type":"textarea","required":false,"readOnly":false},{"name":"ativo","type":"checkbox","required":true,"readOnly":false}]}
              submitLabel="Adicionar"
              
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
