/**
 * @author nicolas.peters
 * 
 * Contains all strings for the default language (en-us).
 * Version 1 - 08/29/08
 */
if(!ORYX) var ORYX = {};

if(!ORYX.I18N) ORYX.I18N = {};

ORYX.I18N.Language = "ZH_cn"; //Pattern <ISO language code>_<ISO country code> in lower case!

if(!ORYX.I18N.Oryx) ORYX.I18N.Oryx = {};

ORYX.I18N.Oryx.title		= "Oryx";
ORYX.I18N.Oryx.noBackendDefined	= "Caution! \nNo Backend defined.\n The requested model cannot be loaded. Try to load a configuration with a save plugin.";
ORYX.I18N.Oryx.pleaseWait 	= "正在加载，请等待...";
ORYX.I18N.Oryx.notLoggedOn = "未登录";
ORYX.I18N.Oryx.editorOpenTimeout = "The editor does not seem to be started yet. Please check, whether you have a popup blocker enabled and disable it or allow popups for this site. We will never display any commercials on this site.";

if(!ORYX.I18N.AddDocker) ORYX.I18N.AddDocker = {};

ORYX.I18N.AddDocker.group = "Docker";
ORYX.I18N.AddDocker.add = "Add Docker";
ORYX.I18N.AddDocker.addDesc = "Add a Docker to an edge, by clicking on it";
ORYX.I18N.AddDocker.del = "Delete Docker";
ORYX.I18N.AddDocker.delDesc = "Delete a Docker";

if(!ORYX.I18N.Arrangement) ORYX.I18N.Arrangement = {};

ORYX.I18N.Arrangement.groupZ = "Z轴次序";
ORYX.I18N.Arrangement.btf = "置前";
ORYX.I18N.Arrangement.btfDesc = "置前";
ORYX.I18N.Arrangement.btb = "置后";
ORYX.I18N.Arrangement.btbDesc = "置后";
ORYX.I18N.Arrangement.bf = "提前";
ORYX.I18N.Arrangement.bfDesc = "提前";
ORYX.I18N.Arrangement.bb = "提后";
ORYX.I18N.Arrangement.bbDesc = "提后";
ORYX.I18N.Arrangement.groupA = "对齐";
ORYX.I18N.Arrangement.ab = "下对齐";
ORYX.I18N.Arrangement.abDesc = "底部";
ORYX.I18N.Arrangement.am = "居中对齐m";
ORYX.I18N.Arrangement.amDesc = "中部m";
ORYX.I18N.Arrangement.at = "上对齐";
ORYX.I18N.Arrangement.atDesc = "上部";
ORYX.I18N.Arrangement.al = "左对齐";
ORYX.I18N.Arrangement.alDesc = "左部";
ORYX.I18N.Arrangement.ac = "居中对齐c";
ORYX.I18N.Arrangement.acDesc = "中部c";
ORYX.I18N.Arrangement.ar = "右对齐";
ORYX.I18N.Arrangement.arDesc = "右部";
ORYX.I18N.Arrangement.as = "对准相同尺寸";
ORYX.I18N.Arrangement.asDesc = "相同尺寸";

if(!ORYX.I18N.Edit) ORYX.I18N.Edit = {};

ORYX.I18N.Edit.group = "Edit";
ORYX.I18N.Edit.cut = "Cut";
ORYX.I18N.Edit.cutDesc = "Cuts the selection into an Oryx clipboard";
ORYX.I18N.Edit.copy = "Copy";
ORYX.I18N.Edit.copyDesc = "Copies the selection into an Oryx clipboard";
ORYX.I18N.Edit.paste = "Paste";
ORYX.I18N.Edit.pasteDesc = "Pastes the Oryx clipboard to the canvas";
ORYX.I18N.Edit.del = "Delete";
ORYX.I18N.Edit.delDesc = "Deletes all selected shapes";

if(!ORYX.I18N.EPCSupport) ORYX.I18N.EPCSupport = {};

ORYX.I18N.EPCSupport.group = "EPC";
ORYX.I18N.EPCSupport.exp = "Export EPC";
ORYX.I18N.EPCSupport.expDesc = "Export diagram to EPML";
ORYX.I18N.EPCSupport.imp = "Import EPC";
ORYX.I18N.EPCSupport.impDesc = "Import an EPML file";
ORYX.I18N.EPCSupport.progressExp = "Exporting model";
ORYX.I18N.EPCSupport.selectFile = "Select an EPML (.empl) file to import.";
ORYX.I18N.EPCSupport.file = "File";
ORYX.I18N.EPCSupport.impPanel = "Import EPML File";
ORYX.I18N.EPCSupport.impBtn = "Import";
ORYX.I18N.EPCSupport.close = "Close";
ORYX.I18N.EPCSupport.error = "Error";
ORYX.I18N.EPCSupport.progressImp = "Import...";

if(!ORYX.I18N.ERDFSupport) ORYX.I18N.ERDFSupport = {};

ORYX.I18N.ERDFSupport.exp = "Export to ERDF";
ORYX.I18N.ERDFSupport.expDesc = "Export to ERDF";
ORYX.I18N.ERDFSupport.imp = "Import from ERDF";
ORYX.I18N.ERDFSupport.impDesc = "Import from ERDF";
ORYX.I18N.ERDFSupport.impFailed = "Request for import of ERDF failed.";
ORYX.I18N.ERDFSupport.impFailed2 = "An error while importing occurs! <br/>Please check error message: <br/><br/>";
ORYX.I18N.ERDFSupport.error = "Error";
ORYX.I18N.ERDFSupport.noCanvas = "The xml document has no Oryx canvas node included!";
ORYX.I18N.ERDFSupport.noSS = "The Oryx canvas node has no stencil set definition included!";
ORYX.I18N.ERDFSupport.wrongSS = "The given stencil set does not fit to the current editor!";
ORYX.I18N.ERDFSupport.selectFile = "Select an ERDF (.xml) file or type in the ERDF to import it!";
ORYX.I18N.ERDFSupport.file = "File";
ORYX.I18N.ERDFSupport.impERDF = "Import ERDF";
ORYX.I18N.ERDFSupport.impBtn = "Import";
ORYX.I18N.ERDFSupport.impProgress = "Importing...";
ORYX.I18N.ERDFSupport.close = "Close";
ORYX.I18N.ERDFSupport.deprTitle = "Really export to eRDF?";
ORYX.I18N.ERDFSupport.deprText = "Exporting to eRDF is not recommended anymore because the support will be stopped in future versions of the Oryx editor. If possible, export the model to JSON. Do you want to export anyway?";

if(!ORYX.I18N.jPDLSupport) ORYX.I18N.jPDLSupport = {};

ORYX.I18N.jPDLSupport.group = "ExecBPMN";
ORYX.I18N.jPDLSupport.exp = "Export to jPDL";
ORYX.I18N.jPDLSupport.expDesc = "Export to jPDL";
ORYX.I18N.jPDLSupport.imp = "Import from jPDL";
ORYX.I18N.jPDLSupport.impDesc = "Import jPDL File";
ORYX.I18N.jPDLSupport.impFailedReq = "Request for import of jPDL failed.";
ORYX.I18N.jPDLSupport.impFailedJson = "Transformation of jPDL failed.";
ORYX.I18N.jPDLSupport.impFailedJsonAbort = "Import aborted.";
ORYX.I18N.jPDLSupport.loadSseQuestionTitle = "jBPM stencil set extension needs to be loaded"; 
ORYX.I18N.jPDLSupport.loadSseQuestionBody = "In order to import jPDL, the stencil set extension has to be loaded. Do you want to proceed?";
ORYX.I18N.jPDLSupport.expFailedReq = "Request for export of model failed.";
ORYX.I18N.jPDLSupport.expFailedXml = "Export to jPDL failed. Exporter reported: ";
ORYX.I18N.jPDLSupport.error = "Error";
ORYX.I18N.jPDLSupport.selectFile = "Select an jPDL (.xml) file or type in the jPDL to import it!";
ORYX.I18N.jPDLSupport.file = "File";
ORYX.I18N.jPDLSupport.impJPDL = "Import jPDL";
ORYX.I18N.jPDLSupport.impBtn = "Import";
ORYX.I18N.jPDLSupport.impProgress = "Importing...";
ORYX.I18N.jPDLSupport.close = "Close";

if(!ORYX.I18N.Save) ORYX.I18N.Save = {};

ORYX.I18N.Save.group = "文件";
ORYX.I18N.Save.save = "保存";
ORYX.I18N.Save.saveDesc = "保存";
ORYX.I18N.Save.saveAs = "保存为...";
ORYX.I18N.Save.saveAsDesc = "保存为...";
ORYX.I18N.Save.unsavedData = "有未保存的数据, 在离开前请先保存, 否则将丢失所作的更改!";
ORYX.I18N.Save.newProcess = "新流程";
ORYX.I18N.Save.saveAsTitle = "保存为...";
ORYX.I18N.Save.saveBtn = "保存";
ORYX.I18N.Save.close = "关闭";
ORYX.I18N.Save.savedAs = "保存为";
ORYX.I18N.Save.saved = "已保存!";
ORYX.I18N.Save.failed = "保存失败.";
ORYX.I18N.Save.noRights = "你没有权限保存更改内容.";
ORYX.I18N.Save.saving = "正在保存";
ORYX.I18N.Save.saveAsHint = "流程图保存在:";

if(!ORYX.I18N.File) ORYX.I18N.File = {};

ORYX.I18N.File.group = "文件";
ORYX.I18N.File.print = "打印";
ORYX.I18N.File.printDesc = "打印当前模型";
ORYX.I18N.File.pdf = "导出为PDF";
ORYX.I18N.File.pdfDesc = "导出为PDF";
ORYX.I18N.File.info = "信息";
ORYX.I18N.File.infoDesc = "信息";
ORYX.I18N.File.genPDF = "生成PDF";
ORYX.I18N.File.genPDFFailed = "生成PDF失败.";
ORYX.I18N.File.printTitle = "打印";
ORYX.I18N.File.printMsg = "We are currently experiencing problems with the printing function. We recommend using the PDF Export to print the diagram. Do you really want to continue printing?";

if(!ORYX.I18N.Grouping) ORYX.I18N.Grouping = {};

ORYX.I18N.Grouping.grouping = "正在分组";
ORYX.I18N.Grouping.group = "分组";
ORYX.I18N.Grouping.groupDesc = "给选中图形分组";
ORYX.I18N.Grouping.ungroup = "未分组";
ORYX.I18N.Grouping.ungroupDesc = "删除所有选中图形的分组";

if(!ORYX.I18N.Loading) ORYX.I18N.Loading = {};

ORYX.I18N.Loading.waiting ="请等待...";

if(!ORYX.I18N.PropertyWindow) ORYX.I18N.PropertyWindow = {};

ORYX.I18N.PropertyWindow.name = "名字";
ORYX.I18N.PropertyWindow.value = "值";
ORYX.I18N.PropertyWindow.selected = "选中";
ORYX.I18N.PropertyWindow.clickIcon = "点击图标";
ORYX.I18N.PropertyWindow.add = "新增";
ORYX.I18N.PropertyWindow.rem = "删除";
ORYX.I18N.PropertyWindow.complex = "Editor for a Complex Type";
ORYX.I18N.PropertyWindow.text = "Editor for a Text Type";
ORYX.I18N.PropertyWindow.ok = "确定";
ORYX.I18N.PropertyWindow.cancel = "取消";
ORYX.I18N.PropertyWindow.dateFormat = "m/d/y";

if(!ORYX.I18N.ShapeMenuPlugin) ORYX.I18N.ShapeMenuPlugin = {};

ORYX.I18N.ShapeMenuPlugin.drag = "拖动";
ORYX.I18N.ShapeMenuPlugin.clickDrag = "点击拖动";
ORYX.I18N.ShapeMenuPlugin.morphMsg = "改变形状";

if(!ORYX.I18N.SyntaxChecker) ORYX.I18N.SyntaxChecker = {};

ORYX.I18N.SyntaxChecker.group = "Verification";
ORYX.I18N.SyntaxChecker.name = "Syntax Checker";
ORYX.I18N.SyntaxChecker.desc = "Check Syntax";
ORYX.I18N.SyntaxChecker.noErrors = "There are no syntax errors.";
ORYX.I18N.SyntaxChecker.invalid = "Invalid answer from server.";
ORYX.I18N.SyntaxChecker.checkingMessage = "Checking ...";

if(!ORYX.I18N.FormHandler) ORYX.I18N.FormHandler = {};

ORYX.I18N.FormHandler.group = "FormHandling";
ORYX.I18N.FormHandler.name = "FormHandler";
ORYX.I18N.FormHandler.desc = "Testing from handling";

if(!ORYX.I18N.Deployer) ORYX.I18N.Deployer = {};

ORYX.I18N.Deployer.group = "发布";
ORYX.I18N.Deployer.name = "发布";
ORYX.I18N.Deployer.desc = "发布到引擎";

if(!ORYX.I18N.Tester) ORYX.I18N.Tester = {};

ORYX.I18N.Tester.group = "测试";
ORYX.I18N.Tester.name = "测试流程";
ORYX.I18N.Tester.desc = "Open the test component to test this process definition";

if(!ORYX.I18N.Undo) ORYX.I18N.Undo = {};

ORYX.I18N.Undo.group = "撤销";
ORYX.I18N.Undo.undo = "撤销";
ORYX.I18N.Undo.undoDesc = "撤销上一动作";
ORYX.I18N.Undo.redo = "重做";
ORYX.I18N.Undo.redoDesc = "重做上一位未完成动作";

if(!ORYX.I18N.View) ORYX.I18N.View = {};

ORYX.I18N.View.group = "缩放";
ORYX.I18N.View.zoomIn = "缩小";
ORYX.I18N.View.zoomInDesc = "缩小模型";
ORYX.I18N.View.zoomOut = "放大";
ORYX.I18N.View.zoomOutDesc = "放大模型";
ORYX.I18N.View.zoomStandard = "标准";
ORYX.I18N.View.zoomStandardDesc = "缩放到标准尺寸";
ORYX.I18N.View.zoomFitToModel = "缩放到适应模型";
ORYX.I18N.View.zoomFitToModelDesc = "缩放到适应模型的尺寸";

if(!ORYX.I18N.XFormsSerialization) ORYX.I18N.XFormsSerialization = {};

ORYX.I18N.XFormsSerialization.group = "XForms Serialization";
ORYX.I18N.XFormsSerialization.exportXForms = "XForms Export";
ORYX.I18N.XFormsSerialization.exportXFormsDesc = "Export XForms+XHTML markup";
ORYX.I18N.XFormsSerialization.importXForms = "XForms Import";
ORYX.I18N.XFormsSerialization.importXFormsDesc = "Import XForms+XHTML markup";
ORYX.I18N.XFormsSerialization.noClientXFormsSupport = "No XForms support";
ORYX.I18N.XFormsSerialization.noClientXFormsSupportDesc = "<h2>Your browser does not support XForms. Please install the <a href=\"https://addons.mozilla.org/firefox/addon/824\" target=\"_blank\">Mozilla XForms Add-on</a> for Firefox.</h2>";
ORYX.I18N.XFormsSerialization.ok = "Ok";
ORYX.I18N.XFormsSerialization.selectFile = "Select a XHTML (.xhtml) file or type in the XForms+XHTML markup to import it!";
ORYX.I18N.XFormsSerialization.selectCss = "Please insert url of css file";
ORYX.I18N.XFormsSerialization.file = "File";
ORYX.I18N.XFormsSerialization.impFailed = "Request for import of document failed.";
ORYX.I18N.XFormsSerialization.impTitle = "Import XForms+XHTML document";
ORYX.I18N.XFormsSerialization.expTitle = "Export XForms+XHTML document";
ORYX.I18N.XFormsSerialization.impButton = "Import";
ORYX.I18N.XFormsSerialization.impProgress = "Importing...";
ORYX.I18N.XFormsSerialization.close = "Close";

/** New Language Properties: 08.12.2008 */

ORYX.I18N.PropertyWindow.title = "Properties";

if(!ORYX.I18N.ShapeRepository) ORYX.I18N.ShapeRepository = {};
ORYX.I18N.ShapeRepository.title = "Shape Repository";

ORYX.I18N.Save.dialogDesciption = "Please enter a name, a description and a comment.";
ORYX.I18N.Save.dialogLabelTitle = "Title";
ORYX.I18N.Save.dialogLabelDesc = "Description";
ORYX.I18N.Save.dialogLabelType = "Type";
ORYX.I18N.Save.dialogLabelComment = "Revision comment";

if(!ORYX.I18N.Perspective) ORYX.I18N.Perspective = {};
ORYX.I18N.Perspective.no = "No Perspective"
ORYX.I18N.Perspective.noTip = "Unload the current perspective"

/** New Language Properties: 21.04.2009 */
ORYX.I18N.JSONSupport = {
    imp: {
        name: "Import from JSON",
        desc: "Imports a model from JSON",
        group: "Export",
        selectFile: "Select an JSON (.json) file or type in JSON to import it!",
        file: "File",
        btnImp: "Import",
        btnClose: "Close",
        progress: "Importing ...",
        syntaxError: "Syntax error"
    },
    exp: {
        name: "Export to JSON",
        desc: "Exports current model to JSON",
        group: "Export"
    }
};

/** New Language Properties: 09.05.2009 */
if(!ORYX.I18N.JSONImport) ORYX.I18N.JSONImport = {};

ORYX.I18N.JSONImport.title = "JSON Import";
ORYX.I18N.JSONImport.wrongSS = "The stencil set of the imported file ({0}) does not match to the loaded stencil set ({1})."

/** New Language Properties: 14.05.2009 */
if(!ORYX.I18N.RDFExport) ORYX.I18N.RDFExport = {};
ORYX.I18N.RDFExport.group = "Export";
ORYX.I18N.RDFExport.rdfExport = "Export to RDF";
ORYX.I18N.RDFExport.rdfExportDescription = "Exports current model to the XML serialization defined for the Resource Description Framework (RDF)";

/** New Language Properties: 15.05.2009*/
if(!ORYX.I18N.SyntaxChecker.BPMN) ORYX.I18N.SyntaxChecker.BPMN={};
ORYX.I18N.SyntaxChecker.BPMN_NO_SOURCE = "An edge must have a source.";
ORYX.I18N.SyntaxChecker.BPMN_NO_TARGET = "An edge must have a target.";
ORYX.I18N.SyntaxChecker.BPMN_DIFFERENT_PROCESS = "Source and target node must be contained in the same process.";
ORYX.I18N.SyntaxChecker.BPMN_SAME_PROCESS = "Source and target node must be contained in different pools.";
ORYX.I18N.SyntaxChecker.BPMN_FLOWOBJECT_NOT_CONTAINED_IN_PROCESS = "A flow object must be contained in a process.";
ORYX.I18N.SyntaxChecker.BPMN_ENDEVENT_WITHOUT_INCOMING_CONTROL_FLOW = "An end event must have an incoming sequence flow.";
ORYX.I18N.SyntaxChecker.BPMN_STARTEVENT_WITHOUT_OUTGOING_CONTROL_FLOW = "A start event must have an outgoing sequence flow.";
ORYX.I18N.SyntaxChecker.BPMN_STARTEVENT_WITH_INCOMING_CONTROL_FLOW = "Start events must not have incoming sequence flows.";
ORYX.I18N.SyntaxChecker.BPMN_ATTACHEDINTERMEDIATEEVENT_WITH_INCOMING_CONTROL_FLOW = "Attached intermediate events must not have incoming sequence flows.";
ORYX.I18N.SyntaxChecker.BPMN_ATTACHEDINTERMEDIATEEVENT_WITHOUT_OUTGOING_CONTROL_FLOW = "Attached intermediate events must have exactly one outgoing sequence flow.";
ORYX.I18N.SyntaxChecker.BPMN_ENDEVENT_WITH_OUTGOING_CONTROL_FLOW = "End events must not have outgoing sequence flows.";
ORYX.I18N.SyntaxChecker.BPMN_EVENTBASEDGATEWAY_BADCONTINUATION = "Event-based gateways must not be followed by gateways or subprocesses.";
ORYX.I18N.SyntaxChecker.BPMN_NODE_NOT_ALLOWED = "Node type is not allowed.";

if(!ORYX.I18N.SyntaxChecker.IBPMN) ORYX.I18N.SyntaxChecker.IBPMN={};
ORYX.I18N.SyntaxChecker.IBPMN_NO_ROLE_SET = "Interactions must have a sender and a receiver role set";
ORYX.I18N.SyntaxChecker.IBPMN_NO_INCOMING_SEQFLOW = "This node must have incoming sequence flow.";
ORYX.I18N.SyntaxChecker.IBPMN_NO_OUTGOING_SEQFLOW = "This node must have outgoing sequence flow.";

if(!ORYX.I18N.SyntaxChecker.InteractionNet) ORYX.I18N.SyntaxChecker.InteractionNet={};
ORYX.I18N.SyntaxChecker.InteractionNet_SENDER_NOT_SET = "Sender not set";
ORYX.I18N.SyntaxChecker.InteractionNet_RECEIVER_NOT_SET = "Receiver not set";
ORYX.I18N.SyntaxChecker.InteractionNet_MESSAGETYPE_NOT_SET = "Message type not set";
ORYX.I18N.SyntaxChecker.InteractionNet_ROLE_NOT_SET = "Role not set";

if(!ORYX.I18N.SyntaxChecker.EPC) ORYX.I18N.SyntaxChecker.EPC={};
ORYX.I18N.SyntaxChecker.EPC_NO_SOURCE = "Each edge must have a source.";
ORYX.I18N.SyntaxChecker.EPC_NO_TARGET = "Each edge must have a target.";
ORYX.I18N.SyntaxChecker.EPC_NOT_CONNECTED = "Node must be connected with edges.";
ORYX.I18N.SyntaxChecker.EPC_NOT_CONNECTED_2 = "Node must be connected with more edges.";
ORYX.I18N.SyntaxChecker.EPC_TOO_MANY_EDGES = "Node has too many connected edges.";
ORYX.I18N.SyntaxChecker.EPC_NO_CORRECT_CONNECTOR = "Node is no correct connector.";
ORYX.I18N.SyntaxChecker.EPC_MANY_STARTS = "There must be only one start event.";
ORYX.I18N.SyntaxChecker.EPC_FUNCTION_AFTER_OR = "There must be no functions after a splitting OR/XOR.";
ORYX.I18N.SyntaxChecker.EPC_PI_AFTER_OR = "There must be no process interface after a splitting OR/XOR.";
ORYX.I18N.SyntaxChecker.EPC_FUNCTION_AFTER_FUNCTION =  "There must be no function after a function.";
ORYX.I18N.SyntaxChecker.EPC_EVENT_AFTER_EVENT =  "There must be no event after an event.";
ORYX.I18N.SyntaxChecker.EPC_PI_AFTER_FUNCTION =  "There must be no process interface after a function.";
ORYX.I18N.SyntaxChecker.EPC_FUNCTION_AFTER_PI =  "There must be no function after a process interface.";
ORYX.I18N.SyntaxChecker.EPC_SOURCE_EQUALS_TARGET = "Edge must connect two distinct nodes."

if(!ORYX.I18N.SyntaxChecker.PetriNet) ORYX.I18N.SyntaxChecker.PetriNet={};
ORYX.I18N.SyntaxChecker.PetriNet_NOT_BIPARTITE = "The graph is not bipartite";
ORYX.I18N.SyntaxChecker.PetriNet_NO_LABEL = "Label not set for a labeled transition";
ORYX.I18N.SyntaxChecker.PetriNet_NO_ID = "There is a node without id";
ORYX.I18N.SyntaxChecker.PetriNet_SAME_SOURCE_AND_TARGET = "Two flow relationships have the same source and target";
ORYX.I18N.SyntaxChecker.PetriNet_NODE_NOT_SET = "A node is not set for a flowrelationship";

/** New Language Properties: 02.06.2009*/
ORYX.I18N.Edge = "Edge";
ORYX.I18N.Node = "Node";

/** New Language Properties: 03.06.2009*/
ORYX.I18N.SyntaxChecker.notice = "Move the mouse over a red cross icon to see the error message.";

/** New Language Properties: 05.06.2009*/
if(!ORYX.I18N.RESIZE) ORYX.I18N.RESIZE = {};
ORYX.I18N.RESIZE.tipGrow = "Increase canvas size:";
ORYX.I18N.RESIZE.tipShrink = "Decrease canvas size:";
ORYX.I18N.RESIZE.N = "Top";
ORYX.I18N.RESIZE.W = "Left";
ORYX.I18N.RESIZE.S ="Down";
ORYX.I18N.RESIZE.E ="Right";

/** New Language Properties: 15.07.2009*/
if(!ORYX.I18N.Layouting) ORYX.I18N.Layouting ={};
ORYX.I18N.Layouting.doing = "Layouting...";

/** New Language Properties: 18.08.2009*/
ORYX.I18N.SyntaxChecker.MULT_ERRORS = "Multiple Errors";

/** New Language Properties: 08.09.2009*/
if(!ORYX.I18N.PropertyWindow) ORYX.I18N.PropertyWindow = {};
ORYX.I18N.PropertyWindow.oftenUsed = "Often used";
ORYX.I18N.PropertyWindow.moreProps = "More Properties";

/** New Language Properties 01.10.2009 */
if(!ORYX.I18N.SyntaxChecker.BPMN2) ORYX.I18N.SyntaxChecker.BPMN2 = {};

ORYX.I18N.SyntaxChecker.BPMN2_DATA_INPUT_WITH_INCOMING_DATA_ASSOCIATION = "A Data Input must not have any incoming Data Associations.";
ORYX.I18N.SyntaxChecker.BPMN2_DATA_OUTPUT_WITH_OUTGOING_DATA_ASSOCIATION = "A Data Output must not have any outgoing Data Associations.";
ORYX.I18N.SyntaxChecker.BPMN2_EVENT_BASED_TARGET_WITH_TOO_MANY_INCOMING_SEQUENCE_FLOWS = "Targets of Event-based Gateways may only have one incoming Sequence Flow.";

/** New Language Properties 02.10.2009 */
ORYX.I18N.SyntaxChecker.BPMN2_EVENT_BASED_WITH_TOO_LESS_OUTGOING_SEQUENCE_FLOWS = "An Event-based Gateway must have two or more outgoing Sequence Flows.";
ORYX.I18N.SyntaxChecker.BPMN2_EVENT_BASED_EVENT_TARGET_CONTRADICTION = "If Message Intermediate Events are used in the configuration, then Receive Tasks must not be used and vice versa.";
ORYX.I18N.SyntaxChecker.BPMN2_EVENT_BASED_WRONG_TRIGGER = "Only the following Intermediate Event triggers are valid: Message, Signal, Timer, Conditional and Multiple.";
ORYX.I18N.SyntaxChecker.BPMN2_EVENT_BASED_WRONG_CONDITION_EXPRESSION = "The outgoing Sequence Flows of the Event Gateway must not have a condition expression.";
ORYX.I18N.SyntaxChecker.BPMN2_EVENT_BASED_NOT_INSTANTIATING = "The Gateway does not meet the conditions to instantiate the process. Please use a start event or an instantiating attribute for the gateway.";

/** New Language Properties 05.10.2009 */
ORYX.I18N.SyntaxChecker.BPMN2_GATEWAYDIRECTION_MIXED_FAILURE = "The Gateway must have both multiple incoming and outgoing Sequence Flows.";
ORYX.I18N.SyntaxChecker.BPMN2_GATEWAYDIRECTION_CONVERGING_FAILURE = "The Gateway must have multiple incoming but most NOT have multiple outgoing Sequence Flows.";
ORYX.I18N.SyntaxChecker.BPMN2_GATEWAYDIRECTION_DIVERGING_FAILURE = "The Gateway must NOT have multiple incoming but must have multiple outgoing Sequence Flows.";
ORYX.I18N.SyntaxChecker.BPMN2_GATEWAY_WITH_NO_OUTGOING_SEQUENCE_FLOW = "A Gateway must have a minimum of one outgoing Sequence Flow.";
ORYX.I18N.SyntaxChecker.BPMN2_RECEIVE_TASK_WITH_ATTACHED_EVENT = "Receive Tasks used in Event Gateway configurations must not have any attached Intermediate Events.";
ORYX.I18N.SyntaxChecker.BPMN2_EVENT_SUBPROCESS_BAD_CONNECTION = "An Event Subprocess must not have any incoming or outgoing Sequence Flow.";

/** New Language Properties 13.10.2009 */
ORYX.I18N.SyntaxChecker.BPMN_MESSAGE_FLOW_NOT_CONNECTED = "At least one side of the Message Flow has to be connected.";

/** New Language Properties 24.11.2009 */
ORYX.I18N.SyntaxChecker.BPMN2_TOO_MANY_INITIATING_MESSAGES = "A Choreography Activity may only have one initiating message.";
ORYX.I18N.SyntaxChecker.BPMN_MESSAGE_FLOW_NOT_ALLOWED = "A Message Flow is not allowed here.";

/** New Language Properties 27.11.2009 */
ORYX.I18N.SyntaxChecker.BPMN2_EVENT_BASED_WITH_TOO_LESS_INCOMING_SEQUENCE_FLOWS = "An Event-based Gateway that is not instantiating must have a minimum of one incoming Sequence Flow.";
ORYX.I18N.SyntaxChecker.BPMN2_TOO_FEW_INITIATING_PARTICIPANTS = "A Choreography Activity must have one initiating Participant (white).";
ORYX.I18N.SyntaxChecker.BPMN2_TOO_MANY_INITIATING_PARTICIPANTS = "A Choreography Acitivity must not have more than one initiating Participant (white)."

ORYX.I18N.SyntaxChecker.COMMUNICATION_AT_LEAST_TWO_PARTICIPANTS = "The communication must be connected to at least two participants.";
ORYX.I18N.SyntaxChecker.MESSAGEFLOW_START_MUST_BE_PARTICIPANT = "The message flow's source must be a participant.";
ORYX.I18N.SyntaxChecker.MESSAGEFLOW_END_MUST_BE_PARTICIPANT = "The message flow's target must be a participant.";
ORYX.I18N.SyntaxChecker.CONV_LINK_CANNOT_CONNECT_CONV_NODES = "The conversation link must connect a communication or sub conversation node with a participant.";
