﻿//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//     Runtime Version:4.0.30319.42000
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

using System.Xml.Serialization;

// 
// This source code was auto-generated by xsd, Version=4.6.1055.0.
// 
namespace EomSlotsForSession {
    using System.Xml.Serialization;


/// <remarks/>
[System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.6.1055.0")]
[System.SerializableAttribute()]
[System.Diagnostics.DebuggerStepThroughAttribute()]
[System.ComponentModel.DesignerCategoryAttribute("code")]
[System.Xml.Serialization.XmlRootAttribute("SlotList", Namespace="", IsNullable=false)]
public partial class SlotListStruct {
    
    /// <remarks/>
    [System.Xml.Serialization.XmlElementAttribute("Slot")]
    public SlotStruct[] Slot;
}

/// <remarks/>
[System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.6.1055.0")]
[System.SerializableAttribute()]
[System.Diagnostics.DebuggerStepThroughAttribute()]
[System.ComponentModel.DesignerCategoryAttribute("code")]
public partial class SlotStruct {
    
    /// <remarks/>
    public int DBID;
    
    /// <remarks/>
    public int RefID;
    
    /// <remarks/>
    public string GUID;
    
    /// <remarks/>
    public string SessionGUID;
    
    /// <remarks/>
    public string Date;
    
    /// <remarks/>
    public string StartTime;
    
    /// <remarks/>
    public string SlotLength;
    
    /// <remarks/>
    public TypeStruct Type;
    
    /// <remarks/>
    public string Status;
    
    /// <remarks/>
    public PatientListStruct PatientList;
    
    /// <remarks/>
    public string Notes;
    
    /// <remarks/>
    public string Reason;
}

/// <remarks/>
[System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.6.1055.0")]
[System.SerializableAttribute()]
[System.Diagnostics.DebuggerStepThroughAttribute()]
[System.ComponentModel.DesignerCategoryAttribute("code")]
public partial class TypeStruct {
    
    /// <remarks/>
    public string TypeID;
    
    /// <remarks/>
    public string Description;
}

/// <remarks/>
[System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.6.1055.0")]
[System.SerializableAttribute()]
[System.Diagnostics.DebuggerStepThroughAttribute()]
[System.ComponentModel.DesignerCategoryAttribute("code")]
public partial class PatientStruct {
    
    /// <remarks/>
    public int DBID;
    
    /// <remarks/>
    public int RefID;
    
    /// <remarks/>
    public string GUID;
    
    /// <remarks/>
    public string Title;
    
    /// <remarks/>
    public string FirstNames;
    
    /// <remarks/>
    public string Surname;
    
    /// <remarks/>
    public string FullName;
}

/// <remarks/>
[System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "4.6.1055.0")]
[System.SerializableAttribute()]
[System.Diagnostics.DebuggerStepThroughAttribute()]
[System.ComponentModel.DesignerCategoryAttribute("code")]
public partial class PatientListStruct {
    
    /// <remarks/>
    public PatientStruct Patient;
}
}
