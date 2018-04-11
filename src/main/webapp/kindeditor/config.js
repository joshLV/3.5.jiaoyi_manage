//编辑器的默认配置信息
var editOptions = {
	uploadJson : '../file/doUploadingFileJSON.do', // 文件上传路径
	urlType : 'domain',// 绝对URL（包含域名）

	// fileManagerJson : '../jsp/file_manager_json.asp',//文件浏览
	allowFileManager : false, // 开启文件浏览？
	items : [ 'source', '|', 'fontname', 'fontsize', '|', 'forecolor',
			'hilitecolor', 'bold', 'italic', 'underline', 'strikethrough',
			'removeformat', '|', 'justifyleft', 'justifycenter',
			'justifyright', 'insertorderedlist', 'insertunorderedlist', '|',
			'hr','link','unlink' ],
			htmlTags :{
		        font : ['color', 'size', 'face', '.background-color'],
		        span : [
		                '.color', '.background-color', '.font-size', '.font-family', '.background',
		                '.font-weight', '.font-style', '.text-decoration', '.vertical-align', '.line-height'
		        ],
		        div : [
		                'align', '.border', '.margin', '.padding', '.text-align', '.color',
		                '.background-color', '.font-size', '.font-family', '.font-weight', '.background',
		                '.font-style', '.text-decoration', '.vertical-align', '.margin-left'
		        ],
		        table: [
		                'border', 'cellspacing', 'cellpadding', 'width', 'height', 'align', 'bordercolor',
		                '.padding', '.margin', '.border', 'bgcolor', '.text-align', '.color', '.background-color',
		                '.font-size', '.font-family', '.font-weight', '.font-style', '.text-decoration', '.background',
		                '.width', '.height', '.border-collapse'
		        ],
		        'td,th': [
		                'align', 'valign', 'width', 'height', 'colspan', 'rowspan', 'bgcolor',
		                '.text-align', '.color', '.background-color', '.font-size', '.font-family', '.font-weight',
		                '.font-style', '.text-decoration', '.vertical-align', '.background', '.border'
		        ],
		        a : ['href', 'target', 'name'],
		        embed : ['src', 'width', 'height', 'type', 'loop', 'autostart', 'quality', '.width', '.height', 'align', 'allowscriptaccess'],
		        img : ['src', 'width', 'height', 'border', 'alt', 'title', 'align', '.width', '.height', '.border'],
		        'p,ol,ul,li,blockquote,h1,h2,h3,h4,h5,h6' : [
		                'align', '.text-align', '.color', '.background-color', '.font-size', '.font-family', '.background',
		                '.font-weight', '.font-style', '.text-decoration', '.vertical-align', '.text-indent', '.margin-left'
		        ],
		        pre : ['class'],
		        hr : ['class', '.page-break-after'],
		        'br,tbody,tr,strong,b,sub,sup,em,i,u,strike,s,del' : []
		}
};
var options = {
	uploadJson : '../editorImage/imageUpload.do', // 文件上传路径
	urlType : 'domain',// 绝对URL（包含域名）
	fileManagerJson : '../editorImage/imageList_json.do',// 文件浏览
	allowFileManager : true, // 开启文件浏览？
	items : [ 'source', '|', 'fontname', 'fontsize', '|', 'forecolor',
			'hilitecolor', 'bold', 'italic', 'underline', 'strikethrough',
			'removeformat', '|', 'justifyleft', 'justifycenter',
			'justifyright', 'insertorderedlist', 'insertunorderedlist', '|',
			'image', 'hr','link','unlink' ],
	htmlTags :{
        font : ['color', 'size', 'face', '.background-color'],
        span : [
                '.color', '.background-color', '.font-size', '.font-family', '.background',
                '.font-weight', '.font-style', '.text-decoration', '.vertical-align', '.line-height'
        ],
        div : [
                'align', '.border', '.margin', '.padding', '.text-align', '.color',
                '.background-color', '.font-size', '.font-family', '.font-weight', '.background',
                '.font-style', '.text-decoration', '.vertical-align', '.margin-left'
        ],
        table: [
                'border', 'cellspacing', 'cellpadding', 'width', 'height', 'align', 'bordercolor',
                '.padding', '.margin', '.border', 'bgcolor', '.text-align', '.color', '.background-color',
                '.font-size', '.font-family', '.font-weight', '.font-style', '.text-decoration', '.background',
                '.width', '.height', '.border-collapse'
        ],
        'td,th': [
                'align', 'valign', 'width', 'height', 'colspan', 'rowspan', 'bgcolor',
                '.text-align', '.color', '.background-color', '.font-size', '.font-family', '.font-weight',
                '.font-style', '.text-decoration', '.vertical-align', '.background', '.border'
        ],
        a : ['href', 'target', 'name'],
        embed : ['src', 'width', 'height', 'type', 'loop', 'autostart', 'quality', '.width', '.height', 'align', 'allowscriptaccess'],
        img : ['src', 'width', 'height', 'border', 'alt', 'title', 'align', '.width', '.height', '.border'],
        'p,ol,ul,li,blockquote,h1,h2,h3,h4,h5,h6' : [
                'align', '.text-align', '.color', '.background-color', '.font-size', '.font-family', '.background',
                '.font-weight', '.font-style', '.text-decoration', '.vertical-align', '.text-indent', '.margin-left'
        ],
        pre : ['class'],
        hr : ['class', '.page-break-after'],
        'br,tbody,tr,strong,b,sub,sup,em,i,u,strike,s,del' : []
}
};